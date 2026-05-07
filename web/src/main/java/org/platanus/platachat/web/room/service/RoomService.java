package org.platanus.platachat.web.room.service;


import org.platanus.platachat.web.auth.dto.AuthServiceMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.dto.RoomCreateRequest;
import org.platanus.platachat.web.room.dto.RoomsRetrieveResponse;
import org.platanus.platachat.web.room.dto.RoomStatusRequest;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoomService {

    Room createRoom(Room room);

    RoomMember addRoomMember(RoomMember roomMember);

    Room createRoom(RoomCreateRequest roomReqDto, Member m);

    List<Room> getRoomsByMemberId(String memberId);

    Room getRoomById(String id, AuthServiceMemberDto sessionMemberDto);

    Room changeRoomInformation(String roomId, RoomStatusRequest roomReqDto, AuthServiceMemberDto sessionMemberDto);

    Room changeRoomOwner(String roomId, RoomStatusRequest requestDto, AuthServiceMemberDto sessionMemberDto);

    Page<Room> getRoomsByVisibleAsPaging(int page);

    Page<Room> getRoomsByVisibleAsPaging(int page, int size);

    Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page);

    Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page, int size);

    Page<RoomsRetrieveResponse> getRoomDtosByMemberIdAsPaging(String memberId, int page);

    Page<RoomsRetrieveResponse> getRoomDtosByMemberIdAsPaging(String memberId, int page, int size);

    void exitRoom(String roomId, AuthServiceMemberDto sessionMemberDto);

    void joinRoom(String roomId, AuthServiceMemberDto sessionMemberDto);

    boolean validateRoomMemberInChat(String roomId, String memberId);

    void endChat(String roomId, AuthServiceMemberDto sessionMemberDto);

    Room validateChatSessionAsPublic(String roomId, AuthServiceMemberDto sessionMemberDto);
}
