package org.platanus.platachat.web.room.model;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomRole {
    SYSOP("SYSOP", "관리자"),
    MEMBER("MEMBER", "맴버"),
    OBSERVER("OBSERVER", "옵저버");
    private final String key;
    private final String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
