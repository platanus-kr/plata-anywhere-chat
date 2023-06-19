package org.platanus.platachat.web.chat.dto;

import lombok.*;

/**
 * 채팅 컨트롤러 에러 응답
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatExceptionResponseDto {
    private Long errorCode;
    private String errorMessage;
}
