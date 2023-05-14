package org.platanus.platachat.message.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

//@Slf4j
@Deprecated
//@Component
//@RequiredArgsConstructor
//public class CustomWebFilter  {
public class CustomWebFilter{
//public class CustomWebFilter implements WebFilter {

//    private final SessionHandlerForMVC sessionHandler;

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return sessionHandler.handleRequest(exchange).then(chain.filter(exchange));
//    }

//    private final ServerSecurityContextRepository repository;

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        Mono<WebSession> session = exchange.getSession();
//        session.doOnNext(webSession -> {
//            log.info("session id: {}", webSession.getId());
//        }).subscribe();
//        ServerHttpRequest request = exchange.getRequest();
//        log.info("cookies: {}", request.getCookies().toString());
//        return repository.load(exchange)
//                .flatMap(context -> {
//                    SecurityContextHolder.setContext(context);
//                    return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
//                })
//                .switchIfEmpty(chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext()));
//    }
}

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        Mono<Principal> principalMono = exchange.getPrincipal();
//        principalMono.flatMap(principal -> {
//            Authentication authentication = (Authentication) principal;
//            Object credentials = authentication.getCredentials();
//            System.out.println(credentials);
//
//            return chain.filter(exchange);
//        });
//
//        return chain.filter(exchange);
//    }

//    private String extractSessionKey(ServerHttpRequest request) {
//        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
//        if (cookies != null && cookies.containsKey(sessionKey)) {
//            List<HttpCookie> sessionCookies = cookies.get(sessionKey);
//            if (sessionCookies != null && !sessionCookies.isEmpty()) {
//                return sessionCookies.get(0).getValue();
//            }
//        }
//        return null;
//    }
