package org.platanus.platachat.web.room.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomCreateResponseDto {
	
	/**
	 * 생성된 채팅방 식별자
	 */
	private Long roomId;
	
	private List<RoomMemberDto> participates;
}
