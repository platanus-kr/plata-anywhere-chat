package org.platanus.platachat.web.message.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <h3>메시지 타입</h3>
 * 메시지 타입을 결정합니다. <br/>
 * 텍스트, 이미지URL
 */
@Getter
@RequiredArgsConstructor
public enum MessageType {
    TEXT("TEXT", "텍스트 메시지"),
    IMAGE("IMAGE_URL", "외부 이미지");
    private final String key;
    private final String name;
}
