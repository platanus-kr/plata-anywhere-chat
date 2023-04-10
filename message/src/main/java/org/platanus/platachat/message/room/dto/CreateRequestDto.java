package org.platanus.platachat.message.room.dto;


import org.platanus.platachat.message.room.model.RoomStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRequestDto {
	private String name;
	private String description;
	private Long capacity;
	private RoomStatus roomStatus;
	private String owner;
}
