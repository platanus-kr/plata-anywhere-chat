package org.platanus.platachat.web.room.service;


import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomsRetrieveResponseDto;
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

    Room changeRoomInformation(String roomId, RoomStatusRequestDto roomReqDto, SessionMemberDto sessionMemberDto);

    Room changeRoomOwner(String roomId, RoomStatusRequestDto requestDto, SessionMemberDto sessionMemberDto);

    Page<Room> getRoomsByVisibleAsPaging(int page);

    Page<Room> getRoomsByVisibleAsPaging(int page, int size);

    Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page);

    Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page, int size);

    Page<RoomsRetrieveResponseDto> getRoomDtosByMemberIdAsPaging(String memberId, int page);

    Page<RoomsRetrieveResponseDto> getRoomDtosByMemberIdAsPaging(String memberId, int page, int size);

    void exitRoom(String roomId, SessionMemberDto sessionMemberDto);

    void joinRoom(String roomId, SessionMemberDto sessionMemberDto);

    boolean validateRoomMemberInChat(String roomId, String memberId);

    void endChat(String roomId, SessionMemberDto sessionMemberDto);

    Room validateChatSessionAsPublic(String roomId, SessionMemberDto sessionMemberDto);
}
