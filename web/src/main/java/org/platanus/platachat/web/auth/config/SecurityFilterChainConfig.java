package org.platanus.platachat.web.auth.config;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.app.CustomAuthenticationSuccessHandler;
import org.platanus.platachat.web.auth.oauth2.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final CustomOAuth2UserService customOAuth2MemberService;

    /**
     * 커스텀 컨트롤러나 REST로 인증하려면 Bean 주입 필요.
     *
     * @param authConfig
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/oauth_login",
                                "/error",
                                "/h2-console/**",
                                "/member/join/**", "/member/login/**",
                                "/api/v1/auth", "/api/v1/auth/login", "/api/v1/auth/validate",
                                "/chat/room/random",
                                "/css/**", "/images/**",
                                "/")
                        .permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("SESSION"));
        http.formLogin(formLogin -> formLogin.successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("SESSION"));
        http.cors(cors -> cors.disable());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
