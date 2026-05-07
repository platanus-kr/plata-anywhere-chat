package org.platanus.platachat.web.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;

import java.io.Serializable;

/**
 * auth-service access token에서 구성한 사용자 컨텍스트 DTO
 */
public record AuthServiceMemberDto(
        String id,
        AuthProvider provider,
        String username,
        String profileImage,
        String htmlUrl,
        String nickname,
        String email,
        AppRole appRole,
        @JsonIgnore
        String token
) implements Serializable {

    public static AuthServiceMemberDto from(Member m, String token) {
        return new AuthServiceMemberDto(
                m.getId(),
                m.getProvider(),
                m.getUsername(),
                m.getProfileImage(),
                m.getHtmlUrl(),
                m.getNickname(),
                m.getEmail(),
                m.getAppRole(),
                token
        );
    }

    @Override
    public String toString() {
        return "AuthServiceMemberDto[id=" + id
                + ", provider=" + provider
                + ", username=" + username
                + ", nickname=" + nickname
                + ", email=" + email
                + ", appRole=" + appRole
                + "]";
    }
}
