package org.platanus.platachat.web.auth.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthValidRetrieveResponseDto {

    private String nickname;

    // primitive 타입은 webclient에서 받지 못함.
    private Boolean isAdmission;
    private Boolean isLogin;
    private String message;

}
