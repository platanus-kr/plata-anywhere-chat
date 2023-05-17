package org.platanus.platachat.web.auth.dto;

import lombok.*;

import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;

import java.io.Serial;
import java.io.Serializable;

/**
 * 세션에 직렬화 하기 위한 DTO
 */
@Getter
@Builder
@ToString
//@JsonSerialize(using = SessionMemberDtoSerializer.class)
//@JsonDeserialize(using = SessionMemberDtoDeserializer.class)
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NONE,
//        include = JsonTypeInfo.As.WRAPPER_ARRAY)
@NoArgsConstructor
@AllArgsConstructor
public class SessionMemberDto implements Serializable {

//    @Serial
//    private static final long serialVersionUID = -702177806442606564L;
    private Long id;
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