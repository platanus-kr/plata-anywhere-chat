package org.platanus.platachat.web.room.dto;
import org.platanus.platachat.web.room.model.RoomPublic;

import jakarta.validation.constraints.NotBlank;

public record RoomCreateRequest(

    /**
     * 채팅방 명
     */
    @NotBlank String roomName,

    /**
     * 채팅방 설명
     */
    String description,

    /**
     * 채팅방 대표 이미지
     */
    String imageUrl,

    /**
     * 채팅방 수용 인원
     */
    Long capacity,

    /**
     * 채팅방 공개여부
     */
    @NotBlank RoomPublic roomPublic
) {
}
