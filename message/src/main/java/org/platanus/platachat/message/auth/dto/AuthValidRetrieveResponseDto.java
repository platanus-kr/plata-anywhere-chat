package org.platanus.platachat.message.auth.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthValidRetrieveResponseDto {

    private String nickname;
    private Boolean isAdmission;
    private Boolean isLogin;
    private String message;
}
