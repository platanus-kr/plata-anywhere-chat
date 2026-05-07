package org.platanus.platachat.message.room.model;

import java.time.LocalDateTime;

import org.platanus.platachat.message.auth.dto.AuthServiceValidationResponse;

public class RoomMember {
    private Long sequence;
    private AuthServiceValidationResponse member;
    private LocalDateTime joinDateTime;
    private LocalDateTime exitDateTime;
    private RoomRole role;
    private RoomMemberStatus status;
    private LocalDateTime voidEndDateTime;
}
