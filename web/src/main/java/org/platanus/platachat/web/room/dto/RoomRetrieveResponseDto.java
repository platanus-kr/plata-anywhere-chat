package org.platanus.platachat.web.room.dto;

import java.util.ArrayList;
import java.util.List;

import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomRetrieveResponseDto {
	private String roomId;
	
	private String name;
	
	private String description;
	
	private String imageUrl;
	
	private Long capacity;
	
	private RoomStatus roomStatus;
	
	private RoomPublic roomPublic;
	
	private List<RoomMemberResponseDto> participates;
	
	private RoomMemberResponseDto owner;
	
	public static List<RoomRetrieveResponseDto> from(List<Room> list) {
		List<RoomRetrieveResponseDto> dtos = new ArrayList<>();
		for (Room room : list) {
			dtos.add(RoomRetrieveResponseDto.from(room));
		}
		return dtos;
	}
	
	public static RoomRetrieveResponseDto from (Room rm) {
		return RoomRetrieveResponseDto.builder()
				.roomId(rm.getId())
				.name(rm.getName())
				.description(rm.getDescription())
				.imageUrl(rm.getImageUrl())
				.capacity(rm.getCapacity())
				.roomStatus(rm.getRoomStatus())
				.roomPublic(rm.getRoomPublic())
				.participates(null)
				.owner(RoomMemberResponseDto.from(rm.getOwner())) // select 발생 지점
				.build();
	}
}
