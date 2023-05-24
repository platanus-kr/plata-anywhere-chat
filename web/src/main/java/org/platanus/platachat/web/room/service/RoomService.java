package org.platanus.platachat.web.room.service;


import java.util.List;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;

public interface RoomService {
	List<RoomMember> getRoomMemberByRoomId(long id);
	
	Room createRoom(Room room);
	
	RoomMember addRoomMember(RoomMember roomMember);
	
	List<Room> getRoomsByMemberId(Long id);
	
	Room createRoom(RoomCreateRequestDto roomReqDto, Member m);
}
