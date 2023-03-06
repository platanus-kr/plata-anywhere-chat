package org.platanus.platachat.message.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload {
	// 토픽 위에서 지정
	// 타임스템프 위에서 지정
	// 메시지 값만 집어넣으면 됨.
	
	private String message;
	private String imageUrl;
	private MessageType type;
	
}
