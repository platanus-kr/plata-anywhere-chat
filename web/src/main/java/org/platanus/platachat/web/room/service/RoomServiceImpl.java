package org.platanus.platachat.web.room.service;

import java.util.ArrayList;
import java.util.List;

import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
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
