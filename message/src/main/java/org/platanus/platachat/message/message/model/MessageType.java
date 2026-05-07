package org.platanus.platachat.message.message.model;

import lombok.RequiredArgsConstructor;

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
