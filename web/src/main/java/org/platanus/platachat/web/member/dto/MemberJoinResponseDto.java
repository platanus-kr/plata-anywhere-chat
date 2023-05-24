package org.platanus.platachat.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinResponseDto {
    private String id;
    private String username;
    private String nickname;
    private String email;
}
