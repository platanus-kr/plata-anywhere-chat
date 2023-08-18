package org.platanus.platachat.web.auth.app;

import lombok.Getter;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

/**
 * 어플리케이션에서 사용하는 회원 객체(Member)를 Spring Security 의 UserDetails.User 로 변환하는 클래스
 */
@Getter
public class CustomUserDetailsUserAdaptor extends User {

    private final Member member;

    public CustomUserDetailsUserAdaptor(Member m) {
        super(m.getUsername(), m.getPassword(), getAuthorities(m.getAppRole()));
        this.member = m;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(AppRole role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.getKey()));
    }
}
