package org.platanus.platachat.web.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 채팅 컨트롤러 에러 응답
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatExceptionResponse {
    private Long errorCode;
    private String errorMessage;
}
