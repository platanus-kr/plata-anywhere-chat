package org.platanus.platachat.message.room.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomStatus {
    ALIVE("ALIVE", "채팅중"),
    ENDED("ENDED", "채팅종료");

    private final String key;
    private final String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
