package org.platanus.platachat.message.auth.session;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

//@Component
//@RequiredArgsConstructor
@Deprecated
public class CustomServerSecurityContextRepository {
//public class CustomServerSecurityContextRepository implements ServerSecurityContextRepository {

    private static final String SESSION_KEY_PREFIX = "spring:session:sessions:";
    private static final String SESSION_ATTR_KEY_PREFIX = "sessionAttr:";

//    private final ReactiveRedisOperations<String, String> redisOperations;
//    private final SecurityProperties securityProperties;

//    @Override
//    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
//        return null;
//    }
//
//    @Override
//    public Mono<SecurityContext> load(ServerWebExchange exchange) {
////        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
////        if (cookies != null && cookies.containsKey(securityProperties.getSession().getCookie().getName())) {
////            String sessionId = cookies.getFirst(securityProperties.getSession().getCookie().getName()).getValue();
////            String redisKey = SESSION_KEY_PREFIX + sessionId;
////
////            return redisOperations.opsForHash()
////                    .get(redisKey, SESSION_ATTR_KEY_PREFIX + SPRING_SECURITY_CONTEXT_KEY)
////                    .map(value -> {
////                        byte[] contextBytes = Base64.getDecoder().decode((String) value);
////                        Object contextObject = new ObjectInputStream(new ByteArrayInputStream(contextBytes)).readObject();
////                        return new SecurityContextImpl((Authentication) contextObject);
////                    })
////                    .onErrorResume(throwable -> Mono.empty());
////        }
//        return Mono.empty();
//    }
}
