package org.platanus.platachat.message.chat.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MessagePayload {
	@Id
	private String id;
	
	private String roomId;
	private String userId;
	private String nickname;
	private String message;
	private LocalDateTime timestamp;
	private MessageType type;
	
}
