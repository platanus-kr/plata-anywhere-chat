package org.platanus.platachat.message.room;

import org.platanus.platachat.message.room.dto.InviteRequestDto;
import org.platanus.platachat.message.room.dto.JoinRequestDto;
import org.platanus.platachat.message.room.dto.LeaveRequestDto;
import org.platanus.platachat.message.room.model.Room;

public interface RoomService {
	// part1
	
	
	// part1
	Room join(JoinRequestDto requestDto);
	
	// part1
	void leave(LeaveRequestDto requestDto);
	
	// part2
	void evict(LeaveRequestDto requestDto);
	
	
	// part2
	
	/**
	 * 초대
	 * @param requestDto 초대 대상 정보
	 * @return 초대 후 채팅방 정보
	 */
	Room invite(InviteRequestDto requestDto);
	
	// part3
	//Permission grant(PermissionGrantRequestDto requestDto);
	
	// part3
	//Permission revoke(PermissionGrantRequestDto requestDto);
	
	
}
