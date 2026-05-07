package org.platanus.platachat.web.message.model;

import lombok.RequiredArgsConstructor;

/**
 * <h3>메시지 타입</h3>
 * 메시지 타입을 결정합니다. <br/>
 * 텍스트, 이미지URL
 */
@RequiredArgsConstructor
public enum MessageType {
    TEXT("TEXT", "텍스트 메시지"),
    IMAGE("IMAGE_URL", "외부 이미지");
    private final String key;
    private final String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
