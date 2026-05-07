package org.platanus.platachat.web.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Component
@Profile("!production")
public class AuthServiceDevTokenIssuer {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final Duration TOKEN_TTL = Duration.ofHours(8);

    private final ObjectMapper objectMapper;
    private final String jwtSecret;

    public AuthServiceDevTokenIssuer(ObjectMapper objectMapper,
                                     @Value("${auth-service.jwt.secret:}") String jwtSecret) {
        this.objectMapper = objectMapper;
        this.jwtSecret = jwtSecret;
    }

    public String issue(String userId, String nickname) {
        if (!StringUtils.hasText(jwtSecret)) {
            throw new IllegalArgumentException("auth-service jwt secret is not configured");
        }

        String normalizedUserId = StringUtils.hasText(userId) ? userId : "LOCAL_DEV_USER";
        String displayName = StringUtils.hasText(nickname) ? nickname : "auth-service-local";
        long now = Instant.now().getEpochSecond();

        String header = encode(Map.of("alg", "HS256", "typ", "JWT"));
        String payload = encode(Map.of(
                "sub", normalizedUserId,
                "uid", Integer.toUnsignedLong(normalizedUserId.hashCode()),
                "role", "USER",
                "enabled", true,
                "type", "access",
                "sub_type", "dev",
                "email", normalizedUserId + "@auth-service.local",
                "name", displayName,
                "iat", now,
                "exp", now + TOKEN_TTL.toSeconds()
        ));
        String signingInput = header + "." + payload;
        return signingInput + "." + sign(signingInput);
    }

    private String encode(Map<String, Object> value) {
        try {
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(objectMapper.writeValueAsBytes(value));
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid token payload", e);
        }
    }

    private String sign(String signingInput) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal(signingInput.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid token signature", e);
        }
    }
}
