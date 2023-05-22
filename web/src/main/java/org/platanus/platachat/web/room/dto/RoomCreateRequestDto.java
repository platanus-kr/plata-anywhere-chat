package org.platanus.platachat.web.room.dto;


import javax.validation.constraints.NotBlank;

import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomCreateRequestDto {
	
	/**
	 * 채팅방 명
	 */
	@NotBlank
	private String roomName;
	
	/**
	 * 채팅방 설명
	 */
	private String description;
	
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
