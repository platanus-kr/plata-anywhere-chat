package org.platanus.platachat.message.room.model;

import java.time.LocalDateTime;

import org.platanus.platachat.message.auth.dto.AuthValidRetrieveResponseDto;

public class RoomMember {
	private Long id;
	private AuthValidRetrieveResponseDto member;
	private RoomRole roomRole;
	private MemberStatus status;
	private LocalDateTime joined;
}
