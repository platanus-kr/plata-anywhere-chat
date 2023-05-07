package org.platanus.platachat.message.auth;

import org.platanus.platachat.message.auth.model.Member;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

public class CustomAuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.setLocation(URI.create("/")); // 로그인 성공 후 리다이렉트할 페이지
        response.setStatusCode(HttpStatus.SEE_OTHER);

        //ServerWebExchangeUtils.setAlreadyRouted(exchange);

        // 세션에 SessionMemberDto 객체를 저장합니다.
        return exchange.getSession()
                .doOnNext(session -> {
                    if (authentication.getPrincipal() instanceof Member) {
                        Member member = (Member) authentication.getPrincipal();
                        session.getAttributes().put("member", new SessionMemberDto(member, session.getId()));
                    }
                }).then();
    }
}
