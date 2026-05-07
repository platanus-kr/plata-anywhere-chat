package org.platanus.platachat.message.websocket.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandType {
    SUBSCRIBE("SUBSCRIBE", "구독"),
    MESSAGE("MESSAGE", "메시지");

    private final String key;
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
