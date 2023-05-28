package org.platanus.platachat.web.room.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 방의 공개상태 : 입장 가능, 옵저버, 비공개, 비노출, 입장 금지
 */
@Getter
@RequiredArgsConstructor
public enum RoomPublic {
    // 제한없는 입장이 가능한 방
    AVAILABLE("AVAILABLE", "입장 가능"),

    // 채팅은 불가능하지만 볼수는 있는 방
    OBSERVER("OBSERVER", "옵저버"),

    // 허용된 사람만 입장 가능한 방
    PRIVATE("PRIVATE", "비공개"),

    // 채팅방 목록에 노출되지 않는 방
    INVISIBLE("INVISIBLE", "비노출"),

    // 추가적인 입장이 금지된 방
    VOID("VOID", "입장 금지");

    private final String key;
    private final String name;
}
