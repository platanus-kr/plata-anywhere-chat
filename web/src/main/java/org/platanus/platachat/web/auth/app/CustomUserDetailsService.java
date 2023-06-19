package org.platanus.platachat.web.auth.app;

import lombok.RequiredArgsConstructor;

import org.platanus.platachat.web.constants.MemberConstant;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * CustomAuthenticationProvider 에서 사용할 UserDetailsService 구현체.
 * UserDetails 객체를 반환.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member m = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(MemberConstant.MEMBER_NOT_FOUND_MESSAGE));
//        SessionMemberDto sessionMemberDto = new SessionMemberDto(m, null);
        return new CustomUserDetailsUserAdaptor(m);
//        return new CustomUserDetailsUserAdaptor(sessionMemberDto);
    }
}

