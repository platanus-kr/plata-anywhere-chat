package org.platanus.platachat.message.room.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomMemberStatus {
    ALIVE("ALIVE", "채팅중"),
    WARNING("WARN", "경고"),
    VOID("VOID", "채팅 금지");
    private final String key;
    private final String name;
}