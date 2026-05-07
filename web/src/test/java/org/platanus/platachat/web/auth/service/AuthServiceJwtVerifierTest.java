package org.platanus.platachat.web.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceJwtVerifierTest {

    private static final String SECRET = "this-is-a-local-auth-service-secret-32-bytes";

    private final AuthServiceJwtVerifier verifier = new AuthServiceJwtVerifier(new ObjectMapper(), SECRET);

    @Test
    void verifiesAccessToken() {
        var claims = verifier.verifyAccessToken(token("access", true));

        assertThat(claims.userId()).isEqualTo("TEST_ID");
        assertThat(claims.role()).isEqualTo("USER");
    }

    @Test
    void rejectsRefreshToken() {
        assertThatThrownBy(() -> verifier.verifyAccessToken(token("refresh", true)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("token type");
    }

    @Test
    void rejectsDisabledUser() {
        assertThatThrownBy(() -> verifier.verifyAccessToken(token("access", false)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("disabled");
    }

    private static String token(String type, boolean enabled) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String header = encode(objectMapper, Map.of("alg", "HS256", "typ", "JWT"));
            long now = Instant.now().getEpochSecond();
            String payload = encode(objectMapper, Map.of(
                    "sub", "TEST_ID",
                    "uid", 1L,
                    "role", "USER",
                    "enabled", enabled,
                    "type", type,
                    "sub_type", "web",
                    "iat", now,
                    "exp", now + 3600
            ));
            String signingInput = header + "." + payload;
            return signingInput + "." + sign(signingInput);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String encode(ObjectMapper objectMapper, Map<String, Object> value) throws Exception {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(objectMapper.writeValueAsBytes(value));
    }

    private static String sign(String signingInput) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(mac.doFinal(signingInput.getBytes(StandardCharsets.UTF_8)));
    }
}
