package org.platanus.platachat.web.room.model;

import lombok.RequiredArgsConstructor;

/**
 * 방의 상태 : 채팅중, 채팅 종료
 */
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
