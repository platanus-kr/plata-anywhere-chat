package org.platanus.platachat.web.room.service;


import java.util.List;

import org.platanus.platachat.web.room.model.RoomMember;

public interface RoomService {
	List<RoomMember> getRoomMemberByRoomId(long id);
}
