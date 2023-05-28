package org.platanus.platachat.web.room.service;


import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomStatusRequestDto;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoomService {

    Room createRoom(Room room);

    RoomMember addRoomMember(RoomMember roomMember);

    Room createRoom(RoomCreateRequestDto roomReqDto, Member m);

    List<Room> getRoomsByMemberId(String memberId);

    Room getRoomById(String id, SessionMemberDto sessionMemberDto);

    Room changeRoomInformation(RoomStatusRequestDto roomReqDto, SessionMemberDto sessionMemberDto);

    Room changeRoomOwner(RoomStatusRequestDto requestDto);

    Page<Room> getRoomsByVisibleAsPaging(int page);

    Page<Room> getRoomsByVisibleAsPaging(int page, int size);

    Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page);

    Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page, int size);
}
