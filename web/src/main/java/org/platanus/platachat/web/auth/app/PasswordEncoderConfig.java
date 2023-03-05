package org.platanus.platachat.web.auth.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    /**
     * BCrypt 암호화를 사용하기 위한 PasswordEncoder
     * 가입, 로그인, OAuth2에 사용됨.
     *
     * @return BCrypt 사용
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
