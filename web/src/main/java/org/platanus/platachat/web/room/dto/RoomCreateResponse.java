package org.platanus.platachat.web.room.dto;

import java.util.List;

public record RoomCreateResponse(

    /**
     * 생성된 채팅방 식별자
     */
    String roomId,

    /**
     * 채팅방 참여 인원
     */
    List<RoomMemberResponse> participates
) {
}
