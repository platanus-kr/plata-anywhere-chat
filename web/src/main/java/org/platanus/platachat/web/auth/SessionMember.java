package org.platanus.platachat.web.auth;

import lombok.*;
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
public class SessionMember implements Serializable {
    private Long id;
    private String provider;
    private String username;
    private String profileImage;
    private String htmlUrl;
    private String name;
    private String email;
    private AppRole appRole;
    private String token;

    public SessionMember(Member m, String token) {
        this.id = m.getId();
        this.provider = m.getProvider();
        this.username = m.getUsername();
        this.profileImage = m.getProfileImage();
        this.htmlUrl = m.getHtmlUrl();
        this.appRole = m.getAppRole();
        this.token = token;
    }
}