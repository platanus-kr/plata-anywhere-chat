package org.platanus.platachat.web.room.dto;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomMemberStatus;
import org.platanus.platachat.web.room.model.RoomRole;

import java.time.LocalDateTime;

public record RoomMemberResponse(
        String id,
        String nickname,
        RoomMemberStatus status,
        RoomRole role,
        LocalDateTime joinDateTime
) {

    public static RoomMemberResponse from(Member m, RoomMember rm) {
        return new RoomMemberResponse(
                m.getId(),
                m.getNickname(),
                rm.getStatus(),
                rm.getRole(),
                rm.getJoinDateTime()
        );
    }

    public static RoomMemberResponse from(Member rm) {
        return new RoomMemberResponse(rm.getId(), rm.getNickname(), null, null, null);
    }

    public static RoomMemberResponse from(RoomMember rm) {
        return new RoomMemberResponse(
                rm.getMember().getId(),
                rm.getMember().getNickname(),
                rm.getStatus(),
                rm.getRole(),
                rm.getJoinDateTime()
        );
    }
}
