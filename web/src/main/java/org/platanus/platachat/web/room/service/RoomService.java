package org.platanus.platachat.web.room.service;


import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;

import java.util.List;

public interface RoomService {
    List<RoomMember> getRoomMemberByRoomId(long id);

    Room createRoom(Room room);

    RoomMember addRoomMember(RoomMember roomMember);

    Room createRoom(RoomCreateRequestDto roomReqDto, Member m);

    List<Room> getRoomsByMemberId(String memberId);
}
