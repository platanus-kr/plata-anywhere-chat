package org.platanus.platachat.web.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomMemberStatus;
import org.platanus.platachat.web.room.model.RoomRole;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class RoomMemberResponse {
    private String id;
    private String nickname;
    private RoomMemberStatus status;
    private RoomRole role;
    private LocalDateTime joinDateTime;

    public static RoomMemberResponse from(Member m, RoomMember rm) {
        return RoomMemberResponse.builder()
                .id(m.getId())
                .nickname(m.getNickname())
                .status(rm.getStatus())
                .role(rm.getRole())
                .joinDateTime(rm.getJoinDateTime())
                .build();
    }

    public static RoomMemberResponse from(Member rm) {
        return RoomMemberResponse.builder()
                .id(rm.getId())
                .nickname(rm.getNickname())
                .build();
    }

    public static RoomMemberResponse from(RoomMember rm) {
        return RoomMemberResponse.builder()
                .id(rm.getMember().getId())
                .nickname(rm.getMember().getNickname())
                .role(rm.getRole())
                .joinDateTime(rm.getJoinDateTime())
                .status(rm.getStatus())
                .build();
    }
}
