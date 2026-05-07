package org.platanus.platachat.web.auth.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.platanus.platachat.web.auth.dto.AuthServiceTokenClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Component
public class AuthServiceJwtVerifier {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String JWT_ALGORITHM = "HS256";
    private static final TypeReference<Map<String, Object>> CLAIMS_TYPE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;
    private final String jwtSecret;

    public AuthServiceJwtVerifier(ObjectMapper objectMapper,
                                  @Value("${auth-service.jwt.secret:}") String jwtSecret) {
        this.objectMapper = objectMapper;
        this.jwtSecret = jwtSecret;
    }

    public AuthServiceTokenClaims verifyAccessToken(String token) {
        if (!StringUtils.hasText(jwtSecret)) {
            throw new IllegalArgumentException("auth-service jwt secret is not configured");
        }
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("invalid token format");
        }

        Map<String, Object> header = readJson(parts[0]);
        if (!JWT_ALGORITHM.equals(header.get("alg"))) {
            throw new IllegalArgumentException("unsupported token algorithm");
        }

        verifySignature(parts);
        Map<String, Object> claims = readJson(parts[1]);
        verifyExpiration(claims);

        AuthServiceTokenClaims tokenClaims = AuthServiceTokenClaims.from(claims);
        if (!StringUtils.hasText(tokenClaims.userId())) {
            throw new IllegalArgumentException("missing token subject");
        }
        if (!"access".equals(tokenClaims.tokenType())) {
            throw new IllegalArgumentException("unsupported token type");
        }
        if (!tokenClaims.enabled()) {
            throw new IllegalArgumentException("disabled account");
        }
        return tokenClaims;
    }

    private Map<String, Object> readJson(String encoded) {
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(encoded);
            return objectMapper.readValue(decoded, CLAIMS_TYPE);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid token payload", e);
        }
    }

    private void verifySignature(String[] parts) {
        try {
            String signingInput = parts[0] + "." + parts[1];
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
            byte[] expected = mac.doFinal(signingInput.getBytes(StandardCharsets.UTF_8));
            byte[] actual = Base64.getUrlDecoder().decode(parts[2]);
            if (!MessageDigest.isEqual(expected, actual)) {
                throw new IllegalArgumentException("invalid token signature");
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid token signature", e);
        }
    }

    private void verifyExpiration(Map<String, Object> claims) {
        Object exp = claims.get("exp");
        if (!(exp instanceof Number number)) {
            throw new IllegalArgumentException("missing token expiration");
        }
        Instant expiresAt = Instant.ofEpochSecond(number.longValue());
        if (!expiresAt.isAfter(Instant.now())) {
            throw new IllegalArgumentException("expired token");
        }
    }
}
