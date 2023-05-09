package org.platanus.platachat.web.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.platanus.platachat.web.auth.serialize.SessionMemberDtoDeserializer;
import org.platanus.platachat.web.auth.serialize.SessionMemberDtoSerializer;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;

import java.io.Serializable;

/**
 * 세션에 직렬화 하기 위한 DTO
 */
@Getter
@Builder
@ToString
@JsonSerialize(using = SessionMemberDtoSerializer.class)
@JsonDeserialize(using = SessionMemberDtoDeserializer.class)
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