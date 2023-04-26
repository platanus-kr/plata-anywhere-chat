package org.platanus.platachat.message.auth;

import java.io.Serializable;

import org.platanus.platachat.message.auth.model.AppRole;
import org.platanus.platachat.message.auth.model.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 세션에 직렬화 하기 위한 DTO
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SessionMemberDto implements Serializable {
    private Long id;
    private String provider;
    private String username;
    private String profileImage;
    private String htmlUrl;
    private String nickname;
    private String email;
    private AppRole appRole;
    private String token;

    public SessionMemberDto(Member m, String token) {
        this.id = m.getId();
        this.provider = m.getProvider();
        this.username = m.getUsername();
        this.profileImage = m.getProfileImage();
        this.htmlUrl = m.getHtmlUrl();
        this.nickname = m.getNickname();
        this.email = m.getEmail();
        this.appRole = m.getAppRole();
        this.token = token;
    }
}