package org.platanus.platachat.message.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {
    /**
     * 여기는 Spring MVC랑 다르다고함.
     * SpringSecurityContext가 기본적으로 ThreadLocal 기반이라 그런가봄
     * 잘 찾아보고 구현하자.
     */

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        http.authorizeExchange()
                //.pathMatchers("/oauth_login", "/error", "/h2-console/**").permitAll()
                //.pathMatchers("/member/join/**", "/member/login/**").permitAll()
                //.pathMatchers("/api/v1/auth", "/api/v1/auth/login").permitAll()
                //.pathMatchers("/message/**").permitAll() // 테스트를 위한 임시 개방
                //.pathMatchers("/css/**").permitAll() // 테스트를 위한 임시 개방
                .pathMatchers("/**").permitAll()
                .anyExchange().authenticated()
                .and()
                //.oauth2Login()
                //.authenticationSuccessHandler(new CustomAuthenticationSuccessHandler())
                //.and()
                //.logout().logoutUrl("/logout").logoutSuccessHandler(new RedirectToLogoutSuccessHandler(URI.create("/")))
                //.and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable();

        return http.build();
    }
}
