package org.platanus.platachat.message.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.platanus.platachat.message.websocket.acceptance.AuthServiceTokenFixture;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceJwtVerifierTest {

    private static final String SECRET = "this-is-a-local-auth-service-secret-32-bytes";

    private final AuthServiceJwtVerifier verifier = new AuthServiceJwtVerifier(new ObjectMapper(), SECRET);

    @Test
    void verifiesAccessToken() {
        var claims = verifier.verifyAccessToken(AuthServiceTokenFixture.accessToken("TEST_ID", "TEST1"));

        assertThat(claims.userId()).isEqualTo("TEST_ID");
        assertThat(claims.displayName()).isEqualTo("TEST1");
    }
}
