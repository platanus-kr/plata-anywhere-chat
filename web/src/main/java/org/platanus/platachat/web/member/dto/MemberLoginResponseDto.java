package org.platanus.platachat.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.platanus.platachat.web.member.model.AppRole;

/**
 * 일반 로그인 응답을 위한 DTO
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponseDto {
    private String username;
    private AppRole role;
    private String token;
}
