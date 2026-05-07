package org.platanus.platachat.web.auth.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.auth.service.AuthServiceDevTokenIssuer;
import org.platanus.platachat.web.auth.service.AuthServiceJwtVerifier;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceDevControllerTest {

    private static final String SECRET = "this-is-a-local-auth-service-secret-32-bytes";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthServiceDevController controller = new AuthServiceDevController(
            new AuthServiceDevTokenIssuer(objectMapper, SECRET)
    );
    private final AuthServiceJwtVerifier verifier = new AuthServiceJwtVerifier(objectMapper, SECRET);

    @Test
    void loginIssuesAccessTokenCookie() {
        MockHttpServletResponse response = new MockHttpServletResponse();

        String view = controller.login("DEV_USER", "Dev User", "/chat/lobby", response);

        assertThat(view).isEqualTo("redirect:/chat/lobby");
        String setCookie = response.getHeader(HttpHeaders.SET_COOKIE);
        assertThat(setCookie)
                .startsWith("PAC_ACCESS_TOKEN=")
                .contains("HttpOnly")
                .contains("Path=/")
                .contains("SameSite=Lax");

        String token = setCookie.substring("PAC_ACCESS_TOKEN=".length()).split(";", 2)[0];
        var claims = verifier.verifyAccessToken(token);
        assertThat(claims.userId()).isEqualTo("DEV_USER");
        assertThat(claims.name()).isEqualTo("Dev User");
    }

    @Test
    void loginRejectsExternalRedirects() {
        MockHttpServletResponse response = new MockHttpServletResponse();

        String view = controller.login("DEV_USER", "Dev User", "https://example.com", response);

        assertThat(view).isEqualTo("redirect:/chat/lobby");
    }
}
