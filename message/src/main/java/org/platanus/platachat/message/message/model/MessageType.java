package org.platanus.platachat.message.message.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
	TEXT("TEXT", "텍스트 메시지"),
	IMAGE("IMAGE_URL", "외부 이미지");
	private final String key;
	private final String name;
}
