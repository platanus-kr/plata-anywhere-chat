package org.platanus.platachat.web.auth;

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
public class SpringSecurityConfig {

    private final CustomOAuth2UserService customOAuth2MemberService;

    /**
     * 커스텀 컨트롤러나 REST로 인증하려면 Bean 주입 필요.
     * @param authConfig
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth_login", "/error", "/h2-console/**").permitAll()
                .antMatchers("/member/join/**", "/member/login/**").permitAll()
                .antMatchers("/api/v1/auth", "/api/v1/auth/login").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**", "/member/join/**", "/member/login/**")
                .and()
                .headers().frameOptions().sameOrigin();
        http.oauth2Login()
                .successHandler(new CustomAuthenticationSuccessHandler())
                .defaultSuccessUrl("/", true)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies("SESSION");
        http.formLogin()
                .successHandler(new CustomAuthenticationSuccessHandler());
        http.cors().and().csrf().disable();
        return http.build();
    }
}
