package org.platanus.platachat.web.room.service;

import java.util.ArrayList;
import java.util.List;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomCreateResponseDto;
import org.platanus.platachat.web.room.dto.RoomMemberDto;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomMemberStatus;
import org.platanus.platachat.web.room.model.RoomRole;
import org.platanus.platachat.web.room.model.RoomStatus;
import org.platanus.platachat.web.room.repository.RoomMemberRepository;
import org.platanus.platachat.web.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	
	private final RoomMemberRepository roomMemberRepository;
	private final RoomRepository roomRepository;
	
	@Override
	public List<RoomMember> getRoomMemberByRoomId(long id) {
		return new ArrayList<>();
	}
	
	
	@Override
	public Room createRoom(RoomCreateRequestDto roomReqDto, Member m) {
		// 채팅방 생성
		Room r = Room.builder()
				.name(roomReqDto.getRoomName())
				.description(roomReqDto.getDescription())
				.imageUrl(roomReqDto.getImageUrl())
				.capacity(roomReqDto.getCapacity())
				.roomPublic(roomReqDto.getRoomPublic())
				.roomStatus(RoomStatus.ALIVE)
				.owner(m)
				.build();
		Room persistRoom = createRoom(r);
		
		// 채팅방 초기 맴버 생성 (채팅방 오너)
		RoomMember rm = RoomMember.builder()
				.member(m)
				.role(RoomRole.SYSOP)
				.status(RoomMemberStatus.ALIVE)
				.room(persistRoom)
				.build();
		addRoomMember(rm);
		
		return persistRoom;
	}
	
	@Override
	public Room createRoom(Room room) {
		return roomRepository.save(room);
	}
	
	@Override
	public RoomMember addRoomMember(RoomMember roomMember) {
		return roomMemberRepository.save(roomMember);
	}
	
	@Override
	public List<Room> getRoomsByMemberId(Long id) {
		/**
		 * select * from ROOMS as R
		 * inner join ROOM_MEMBER as RM on RM.ROOM_ID = R.ID
		 * where RM.MEMBER_ID = {id}
		 */
		return null;
	}

}
