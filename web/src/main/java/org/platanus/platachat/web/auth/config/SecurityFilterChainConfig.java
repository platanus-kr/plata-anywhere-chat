package org.platanus.platachat.web.auth.config;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.app.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final SecurityCORSConfig corsConfig;

    /**
     * 커스텀 컨트롤러나 REST로 인증하려면 Bean 주입 필요.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

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
    public SecurityFilterChain securityFilterChains(HttpSecurity http,
                                                    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        http.cors(cors -> corsConfig.corsFilter()); // CORS 비활성화 필요
        http.csrf(csrf -> csrf.disable()); // TODO 정책 정해서 동적으로 할 수 있게 바꾸기
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(antMatcher("/oauth_login")).permitAll()
                        .requestMatchers(antMatcher("/error")).permitAll()
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/member/join/**")).permitAll()
                        .requestMatchers(antMatcher("/member/login/**")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/auth/join")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/auth/login")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/auth/validate")).permitAll()
                        .requestMatchers(antMatcher("/chat/room/random")).permitAll()
                        .requestMatchers(antMatcher("/css/**")).permitAll()
                        .requestMatchers(antMatcher("/images/**")).permitAll()
                        .requestMatchers(antMatcher("/")).permitAll()
                        .requestMatchers(antMatcher("/favicon.ico")).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
        http.formLogin(formLogin -> formLogin.successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("SESSION"));
        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("SESSION"));
        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }
}
