package org.platanus.platachat.message.room.model;

import org.platanus.platachat.message.auth.model.Member;

import java.time.LocalDateTime;

public class RoomMember {
    private Long id;
    private Member member;
    private RoomRole roomRole;
    private MemberStatus status;
    private LocalDateTime joined;
}
