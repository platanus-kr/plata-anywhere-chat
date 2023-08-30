package org.platanus.platachat.web.auth.config;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.app.CustomAuthenticationProvider;
import org.platanus.platachat.web.auth.app.PasswordEncoderConfig;
import org.platanus.platachat.web.auth.oauth2.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    /**
     * ### 지금까지 시도해 본 것들
     *
     * AuthenticationManager 빈 주입 안되는것
     * -> AuthenticationProvider 호출로 해결 (완)
     *
     * SecurityFilterChain 필터 안먹는 것
     * - authorizeRequests 변경
     * - authorizeHttpRequests 지정 -> 안됨
     * - securityMatcher 호출 -> 안됨
     *
     * 이거 해보니까 그냥 필터 자체가 안 먹는거 같은데...
     */


    private static final String[] AUTH_WHITELIST = {
            "/oauth_login",
            "/error",
            "/h2-console/**",
            "/member/join/**", "/member/login/**",
            "/api/v1/auth", "/api/v1/auth/login", "/api/v1/auth/validate",
            "/chat/room/random",
            "/css/**", "/images/**",
            "/", "/favicon.ico"
    };
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

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }
//
    // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
    // https://github.com/spring-projects/spring-security/issues/12864#issuecomment-1490089032 // <<- 매우중요. 나랑 같은 삽질한 사람.
    // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#
    //    public SecurityFilterChain securityFilterChains(HttpSecurity http,
//                                                    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
//        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(
//                                "/oauth_login",
//                                "/error",
//                                "/h2-console/**",
//                                "/member/join/**", "/member/login/**",
//                                "/api/v1/auth", "/api/v1/auth/login", "/api/v1/auth/validate",
//                                "/chat/room/random",
//                                "/css/**", "/images/**",
//                                "/", "/favicon.ico")
//                        .permitAll()
//                        .anyRequest().authenticated())
//                .csrf(csrf -> csrf.disable())
//                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
//        http.formLogin(formLogin -> formLogin.successHandler(customAuthenticationSuccessHandler))
//                .logout(logout -> logout.logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .deleteCookies("SESSION"));
//        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(customAuthenticationSuccessHandler))
//                .logout(logout -> logout.logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .deleteCookies("SESSION"));
//        http.cors(cors -> cors.disable());
//        http.csrf(csrf -> csrf.disable());
//        return http.build();
//    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/**"
        );
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .formLogin().disable()
//                .logout().disable()
//                .authorizeHttpRequests(authorize -> authorize
//                                .requestMatchers("/**").permitAll()    // this response is 403 forbidden, expect 404 Not Found because set permitAll
////                        .requestMatchers("/**").permitAll()     // if you release this comment, "/data" response is 404 Not Found
//                )
//                .authorizeHttpRequests().anyRequest().authenticated()
//                .and().build();
//    }
}
