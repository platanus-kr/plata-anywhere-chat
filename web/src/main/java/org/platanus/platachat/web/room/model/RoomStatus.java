package org.platanus.platachat.web.room.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 방의 상태 : 채팅중, 채팅 종료
 */
@Getter
@RequiredArgsConstructor
public enum RoomStatus {
    ALIVE("ALIVE", "채팅중"),
    ENDED("ENDED", "채팅종료");
    private final String key;
    private final String name;
}

