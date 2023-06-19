package org.platanus.platachat.web.auth.app;

import lombok.RequiredArgsConstructor;

import org.platanus.platachat.web.constants.MemberConstant;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * formLogin 인증을 위한 구현체
 * MemberRepository 에서 조회한 비밀번호로 match() 한다.
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUserDetailsUserAdaptor memberContext = (CustomUserDetailsUserAdaptor) customUserDetailsService.loadUserByUsername(username);
//        CustomUserDetailsUserAdaptor memberContext = CustomUserDetailsUserAdaptor.of(customUserDetailsService.loadUserByUsername(username));
        String passwordInDb = memberContext.getMember().getPassword();

        if (!passwordEncoder.matches(password, passwordInDb)) {
            throw new BadCredentialsException(MemberConstant.MEMBER_NOT_MATCH_PASSWORD_MESSAGE);
        }
        return new UsernamePasswordAuthenticationToken(memberContext.getMember(), null, memberContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}



