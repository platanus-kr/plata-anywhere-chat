package org.platanus.platachat.web.member.dto;

import lombok.*;

/**
 * 일반 로그인을 위한 DTO
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequestDto {
    private String username;
    private String password;

    public void setEncodedPassword(String password) {
        this.password = password;
    }
}
