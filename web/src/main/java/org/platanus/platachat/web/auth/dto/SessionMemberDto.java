package org.platanus.platachat.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;

import java.io.Serializable;

/**
 * 세션에 직렬화 하기 위한 DTO
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SessionMemberDto implements Serializable {
    private String id;
    private LoginProvider provider;
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