package org.platanus.platachat.message.chat.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandType {
	SUBSCRIBE("SUBSCRIBE", "구독"),
	MESSAGE("MESSAGE", "메시지");
	
	private final String key;
	private final String value;
}
