package org.platanus.platachat.message.auth.app;

import lombok.extern.slf4j.Slf4j;

import org.platanus.platachat.message.member.model.AppRole;
import org.platanus.platachat.message.member.model.Member;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
//@Component
//@RequiredArgsConstructor
@Deprecated
public class CustomAuthenticationProvider implements ReactiveAuthenticationManager {

    private ReactiveRedisOperations<String, String> redisOperations;
    //private final ReactiveRedisOperations<String, String> redisOperations;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        log.info(username, password);
        return findBySessionKey(authentication)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("사용자를 찾을 수 없습니다.")))
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }

    private Mono<CustomUserDetailsUserAdaptor> findBySessionKey(Authentication authentication) {
        String sessionKey = extractSessionKey(authentication);
        log.info("CAP: {}", sessionKey);
        if (sessionKey == null) {
            return Mono.empty();
        }

        Mono<String> sessionValueMono = redisOperations.opsForValue().get(sessionKey);
        return sessionValueMono.flatMap(sessionValue -> {
            if (sessionValue == null) {
                return Mono.empty();
            }

            return redisOperations.opsForValue().delete(sessionKey)
                    .flatMap(deleteCount -> Mono.just(new CustomUserDetailsUserAdaptor(extractMember(sessionValue))));
        });
    }

    private Member extractMember(String sessionValue) {
        String[] values = sessionValue.split(":");
        if (values.length != 5) {
            throw new RuntimeException("잘못된 세션 데이터입니다.");
        }

        String username = values[0];
        String password = values[1];
        String authorities = values[2];
        AppRole appRole = AppRole.valueOf(values[3]);
        LocalDateTime lastActivated = LocalDateTime.parse(values[4], DateTimeFormatter.ISO_DATE_TIME);

        Member member = Member.builder()
                .username(username)
                .password(password)
                .appRole(appRole)
                .lastActivated(lastActivated)
                .build();

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String authority : authorities.split(",")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return member;
    }

    private String extractSessionKey(Authentication authentication) {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        if (!(token.getDetails() instanceof ServerWebExchange)) {
            return null;
        }

        ServerWebExchange exchange = (ServerWebExchange) token.getDetails();
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();

        if (cookies != null && cookies.containsKey("PAC_SESSIONID")) {
            List<HttpCookie> sessionCookies = cookies.get("PAC_SESSIONID");
            if (sessionCookies != null && !sessionCookies.isEmpty()) {
                return sessionCookies.get(0).getValue();
            }
        }
        return null;
    }
}
