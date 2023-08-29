package org.platanus.platachat.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
