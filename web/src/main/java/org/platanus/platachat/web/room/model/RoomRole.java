package org.platanus.platachat.web.room.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomRole {
    SYSOP("SYSOP", "관리자"),
    MEMBER("MEMBER", "맴버"),
    OBSERVER("OBSERVER", "옵저버");
    private final String key;
    private final String name;
}

