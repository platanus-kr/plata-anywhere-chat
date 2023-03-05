package org.platanus.platachat.web.member.dto;

import lombok.*;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;

import java.time.LocalDateTime;

/**
 * 일반 회원가입을 위한 DTO
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String email;

    public Member toMember() {
        return Member.builder()
                .username(this.username)
                .password(this.password)
                .nickname(this.nickname)
                .email(this.email)
                .provider("web")
                .appRole(AppRole.ROLE_USER)
                .lastActivated(LocalDateTime.now())
                .build();
    }

    public void setEncodedPassword(String password) {
        this.password = password;
    }
}
