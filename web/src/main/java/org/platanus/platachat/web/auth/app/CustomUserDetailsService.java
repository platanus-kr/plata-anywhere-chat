package org.platanus.platachat.web.auth.app;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CustomAuthenticationProvider 에서 사용할 UserDetailsService 구현체.
 * UserDetails 객체를 반환.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member m  = memberService.findByUsername(username);
        return new CustomUserDetailsUserAdaptor(m);
    }
}

