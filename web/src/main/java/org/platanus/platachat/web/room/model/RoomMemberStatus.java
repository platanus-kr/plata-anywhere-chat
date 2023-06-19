package org.platanus.platachat.web.room.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomMemberStatus {
    ALIVE("ALIVE", "채팅중"),
    WARNING("WARN", "경고"),
    VOID("VOID", "채팅 금지"),
    EXITED("EXITED", "퇴장");
    private final String key;
    private final String name;
}