package org.platanus.platachat.web.member.dto;

public record MemberJoinResponse(
        String id,
        String username,
        String nickname,
        String email
) {
}
