package org.platanus.platachat.message.auth.app;

import org.springframework.security.core.userdetails.User;

import lombok.Getter;

/**
 * 어플리케이션에서 사용하는 회원 객체(Member)를 Spring Security 의 UserDetails.User 로 변환하는 클래스
 */
@Getter
@Deprecated
//public class CustomUserDetailsUserAdaptor extends User {
public class CustomUserDetailsUserAdaptor {
    
    //private final Member member;
    //public CustomUserDetailsUserAdaptor(Member m) {
    //    super(m.getUsername(), m.getPassword(), getAuthorities(m.getAppRole()));
    //    this.member = m;
    //}
    //
    //private static Collection<? extends GrantedAuthority> getAuthorities(AppRole role) {
    //    return Collections.singleton(new SimpleGrantedAuthority(role.getKey()));
    //}
}
