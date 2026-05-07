package org.platanus.platachat.message.websocket.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

public class AuthServiceTokenFixture {

    private static final String SECRET = "this-is-a-local-auth-service-secret-32-bytes";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String accessToken(String userId, String name) {
        try {
            String header = encode(Map.of("alg", "HS256", "typ", "JWT"));
            long now = Instant.now().getEpochSecond();
            String payload = encode(Map.of(
                    "sub", userId,
                    "uid", 1L,
                    "role", "USER",
                    "enabled", true,
                    "type", "access",
                    "sub_type", "web",
                    "name", name,
                    "iat", now,
                    "exp", now + 3600
            ));
            String signingInput = header + "." + payload;
            return signingInput + "." + sign(signingInput);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String encode(Map<String, Object> value) throws Exception {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(OBJECT_MAPPER.writeValueAsBytes(value));
    }

    private static String sign(String signingInput) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(mac.doFinal(signingInput.getBytes(StandardCharsets.UTF_8)));
    }
}
