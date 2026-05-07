package org.platanus.platachat.web.auth.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.service.AuthServiceDevTokenIssuer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;

@Controller
@Profile("!production")
@RequestMapping("/dev/auth-service")
@RequiredArgsConstructor
public class AuthServiceDevController {

    private static final String ACCESS_TOKEN_COOKIE = "PAC_ACCESS_TOKEN";
    private static final Duration TOKEN_TTL = Duration.ofHours(8);
    private static final String DEFAULT_REDIRECT = "/chat/lobby";

    private final AuthServiceDevTokenIssuer tokenIssuer;

    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "LOCAL_DEV_USER") String memberId,
                        @RequestParam(required = false) String nickname,
                        @RequestParam(defaultValue = DEFAULT_REDIRECT) String redirect,
                        HttpServletResponse response) {
        String token = tokenIssuer.issue(memberId, nickname);
        ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE, token)
                .httpOnly(true)
                .path("/")
                .maxAge(TOKEN_TTL)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "redirect:" + safeRedirect(redirect);
    }

    private String safeRedirect(String redirect) {
        if (!StringUtils.hasText(redirect)) {
            return DEFAULT_REDIRECT;
        }
        if (!redirect.startsWith("/") || redirect.startsWith("//")) {
            return DEFAULT_REDIRECT;
        }
        if (redirect.contains("\r") || redirect.contains("\n")) {
            return DEFAULT_REDIRECT;
        }
        return redirect;
    }
}
