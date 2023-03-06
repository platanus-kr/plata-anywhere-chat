package org.platanus.platachat.message.room.model;

import java.time.LocalDateTime;

import org.platanus.platachat.message.auth.model.Member;

public class RoomMember {
	private Long id;
	private Member member;
	private RoomRole roomRole;
	private MemberStatus status;
	private LocalDateTime joined;
}
