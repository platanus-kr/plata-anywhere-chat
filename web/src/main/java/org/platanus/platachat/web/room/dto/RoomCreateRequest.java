package org.platanus.platachat.web.room.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.platanus.platachat.web.room.model.RoomPublic;

import jakarta.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
public class RoomCreateRequest {

    /**
     * 채팅방 명
     */
    @NotBlank
    private String roomName;

    /**
     * 채팅방 설명
     */
    private String description;

    /**
     * 채팅방 대표 이미지
     */
    private String imageUrl;

    /**
     * 채팅방 수용 인원
     */
    private Long capacity;

    /**
     * 채팅방 공개여부
     */
    @NotBlank
    private RoomPublic roomPublic;
}
