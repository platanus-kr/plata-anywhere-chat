package org.platanus.platachat.message.room.model;

import java.time.LocalDateTime;

import org.platanus.platachat.message.auth.dto.AuthValidRetrieveResponseDto;

public class RoomMember {
	private Long sequence;
	private AuthValidRetrieveResponseDto member;
	private LocalDateTime joinDateTime;
	private LocalDateTime exitDateTime;
	private RoomRole role;
	private RoomMemberStatus status;
	private LocalDateTime voidEndDateTime;
}
