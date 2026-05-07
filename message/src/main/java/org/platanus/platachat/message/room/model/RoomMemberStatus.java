package org.platanus.platachat.message.room.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomMemberStatus {
    ALIVE("ALIVE", "채팅중"),
    WARNING("WARN", "경고"),
    VOID("VOID", "채팅 금지");

    private final String key;
    private final String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
