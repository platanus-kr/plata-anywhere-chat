package org.platanus.platachat.web.auth;

import lombok.Builder;
import lombok.Getter;
import org.platanus.platachat.web.member.model.Member;

import java.util.Map;

@Getter
@Builder
public class CustomOAuth2MemberDto {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String provider;
    private String providerId;
    private String username;
    private String profileImage;
    private String name;
    private String email;

    public static CustomOAuth2MemberDto ofGitHub(String registrationId, String userNameAttributeKey, Map<String, Object> attributes) {
        return CustomOAuth2MemberDto.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeKey)
                .provider(registrationId)
                .providerId(String.valueOf(attributes.get("id")))
                .username(String.valueOf(attributes.get("login")))
                .profileImage(String.valueOf(attributes.get("avatar_url")))
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .build();
    }

    public Member toMember() {
        return Member.builder()
                .provider(this.provider)
                .username(this.username)
                .profileImage(this.profileImage)
                .nickname(this.name)
                .email(this.email)
                .build();
    }
}
