package org.platanus.platachat.message.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SpringSecurityConfig {
    /**
     * 여기는 Spring MVC랑 다르다고함.
     * SpringSecurityContext가 기본적으로 ThreadLocal 기반이라 그런가봄
     * 잘 찾아보고 구현하자.
     */

//    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomWebFilter customWebFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        http.authorizeExchange()
//                .pathMatchers("/**").permitAll()
                .pathMatchers("/simplemessage/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(customWebFilter, SecurityWebFiltersOrder.FIRST)
//                .addFilterBefore(customWebFilter, SecurityWebFiltersOrder.FIRST)
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
//                .authenticationManager(customAuthenticationProvider)
//                .securityContextRepository(webSessionServerSecurityContextRepository())
//                .addFilterAt(new CustomWebFilter(webSessionServerSecurityContextRepository()), SecurityWebFiltersOrder.SECURITY_CONTEXT_SERVER_WEB_EXCHANGE)
        ;

        return http.build();
    }

//    @Bean
//    public WebSessionServerSecurityContextRepository webSessionServerSecurityContextRepository() {
//        return new WebSessionServerSecurityContextRepository();
//    }
}
