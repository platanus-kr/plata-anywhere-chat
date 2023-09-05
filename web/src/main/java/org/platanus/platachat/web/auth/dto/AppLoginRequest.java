package org.platanus.platachat.web.auth.dto;

import jakarta.validation.constraints.NotBlank;
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
public class AppLoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
