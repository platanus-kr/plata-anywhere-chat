package org.platanus.platachat.web.room.dto;

import java.time.LocalDateTime;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomMemberStatus;
import org.platanus.platachat.web.room.model.RoomRole;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomMemberDto {
	private String id;
	private String nickname;
	private RoomMemberStatus status;
	private RoomRole role;
	private LocalDateTime joinDateTime;
	
	public static RoomMemberDto from(Member m, RoomMember rm) {
		return RoomMemberDto.builder()
				.id(m.getId())
				.nickname(m.getNickname())
				.status(rm.getStatus())
				.role(rm.getRole())
				.joinDateTime(rm.getJoinDateTime())
				.build();
	}
}
