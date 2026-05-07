package org.platanus.platachat.web.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final SecurityCORSConfig corsConfig;
    private final AuthServiceAuthenticationFilter authServiceAuthenticationFilter;

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> response.sendError(401);

    /**
     * securityFilterChains 변경사항
     *
     * <pre>
     *     1. 사용처 변화
     *     https://github.com/spring-projects/spring-security/issues/12864#issuecomment-1490089032
     *
     *     2. cve-2023-34035
     *     https://spring.io/security/cve-2023-34035
     *     https://github.com/jzheaux/cve-2023-34035-mitigations/tree/6.1.x
     * </pre>
     */
    @Bean
    public SecurityFilterChain securityFilterChains(HttpSecurity http) throws Exception {
        http.cors(cors -> corsConfig.corsFilter()); // CORS 비활성화 필요
        http.csrf(csrf -> csrf.disable()); // TODO 정책 정해서 동적으로 할 수 있게 바꾸기
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(antMatcher("/error")).permitAll()
                        .requestMatchers(antMatcher("/dev/auth-service/**")).permitAll()
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/chat/room/random")).permitAll()
                        .requestMatchers(antMatcher("/css/**")).permitAll()
                        .requestMatchers(antMatcher("/images/**")).permitAll()
                        .requestMatchers(antMatcher("/")).permitAll()
                        .requestMatchers(antMatcher("/favicon.ico")).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                .addFilterBefore(authServiceAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedEntryPoint))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .logout(logout -> logout.disable());
        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }
}
