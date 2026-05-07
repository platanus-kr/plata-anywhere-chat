package org.platanus.platachat.web.member.dto;

import org.platanus.platachat.web.auth.dto.AuthProvider;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;

import java.time.LocalDateTime;

/**
 * 일반 회원가입을 위한 DTO
 */
public record MemberJoinRequest(
        String username,
        String password,
        String nickname,
        String email
) {

    public Member toMember(String encodedPassword) {
        return new Member(
                null,
                null,
                AuthProvider.LOCAL,
                this.username,
                encodedPassword,
                this.nickname,
                null,
                null,
                this.email,
                false,
                AppRole.ROLE_USER,
                LocalDateTime.now()
        );
    }
}
