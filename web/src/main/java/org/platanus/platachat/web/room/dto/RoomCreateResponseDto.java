package org.platanus.platachat.web.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class RoomCreateResponseDto {

    /**
     * 생성된 채팅방 식별자
     */
    private String roomId;

    /**
     * 채팅방 참여 인원
     */
    private List<RoomMemberResponseDto> participates;
}
