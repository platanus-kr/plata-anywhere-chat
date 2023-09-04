package org.platanus.platachat.web.auth.config;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.app.CustomAuthenticationProvider;
import org.platanus.platachat.web.auth.app.CustomAuthenticationSuccessHandler;
import org.platanus.platachat.web.auth.app.PasswordEncoderConfig;
import org.platanus.platachat.web.auth.oauth2.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration // 이거니?
//@EnableWebMvc
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    /**
     * ### 지금까지 시도해 본 것들
     * <p>
     * AuthenticationManager 빈 주입 안되는것
     * -> AuthenticationProvider 호출로 해결 (완)
     * <p>
     * SecurityFilterChain 필터 안먹는 것
     * - authorizeRequests 변경
     * - authorizeHttpRequests 지정 -> 안됨
     * - securityMatcher 호출 -> 안됨
     * <p>
     * 이거 해보니까 그냥 필터 자체가 안 먹는거 같은데...
     */

    private final CustomOAuth2UserService customOAuth2MemberService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserDetailsService userDetailService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    /**
     * 커스텀 컨트롤러나 REST로 인증하려면 Bean 주입 필요.
     */
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailService);
//        authProvider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder());
//        return authProvider;
//    }

//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return authentication -> {
//            throw new IllegalStateException("No authentication manager");
//        };
//    }
//    @Bean(name = "authenticationManager")
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public ProviderManager authManagerBean(AuthenticationProvider provider) {
//        return new ProviderManager(provider);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
//
    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
//

    /**
     * securityFilterChains
     *
     * <pre>
     * https://spring.io/security/cve-2023-34035
     * https://github.com/jzheaux/cve-2023-34035-mitigations/tree/6.1.x
     *
     * This method cannot decide whether these patterns are Spring MVC patterns or not.
     * If this endpoint is a Spring MVC endpoint, please use requestMatchers(MvcRequestMatcher);
     * otherwise, please use requestMatchers(AntPathRequestMatcher).
     * </pre>
     */
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }

    // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
    // https://github.com/spring-projects/spring-security/issues/12864#issuecomment-1490089032 // <<- 매우중요. 나랑 같은 삽질한 사람.
    // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#


    @Bean
    public SecurityFilterChain securityFilterChains(HttpSecurity http,
                                                    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
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
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
        http.formLogin(formLogin -> formLogin.successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("SESSION"));
        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("SESSION"));
        http.cors(cors -> cors.disable());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(
//                "/**"
//        );
//    }
}
