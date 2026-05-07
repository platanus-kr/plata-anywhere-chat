package org.platanus.platachat.web.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.dto.AuthServiceTokenClaims;
import org.platanus.platachat.web.auth.dto.AuthServiceMemberDto;
import org.platanus.platachat.web.auth.service.AuthServiceJwtVerifier;
import org.platanus.platachat.web.auth.service.AuthServiceMemberProvisioner;
import org.platanus.platachat.web.member.model.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class AuthServiceAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN_COOKIE = "PAC_ACCESS_TOKEN";
    private static final String DEV_AUTH_SERVICE_PATH = "/dev/auth-service/";

    private final AuthServiceJwtVerifier jwtVerifier;
    private final AuthServiceMemberProvisioner memberProvisioner;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (StringUtils.hasText(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            AuthServiceTokenClaims claims;
            try {
                claims = jwtVerifier.verifyAccessToken(token);
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            Member member = memberProvisioner.findOrCreate(claims);
            AuthServiceMemberDto principal = AuthServiceMemberDto.from(member, token);
            var authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    List.of(new SimpleGrantedAuthority(member.getAppRole().getKey()))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith(DEV_AUTH_SERVICE_PATH);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        String queryToken = request.getParameter("access_token");
        if (StringUtils.hasText(queryToken)) {
            return queryToken;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Stream.of(cookies)
                .filter(cookie -> ACCESS_TOKEN_COOKIE.equals(cookie.getName()))
                .map(Cookie::getValue)
                .filter(StringUtils::hasText)
                .findFirst()
                .orElse(null);
    }
}
