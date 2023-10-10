package org.platanus.platachat.web.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.platanus.platachat.web.constants.AuthConstant;
import org.platanus.platachat.web.member.model.Member;

import java.util.Map;

@Getter
@Builder
public class CustomOAuth2Member {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private LoginProvider provider;
    private String providerId;
    private String username;
    private String profileImage;
    private String name;
    private String email;
    private String blog;

    public static CustomOAuth2Member ofGitHub(String registrationId, String userNameAttributeKey, Map<String, Object> attributes) {
        String name = String.valueOf(ObjectUtils.isEmpty(attributes.get("name")) ? attributes.get("login") : attributes.get("name"));
        return CustomOAuth2Member.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeKey)
                .provider(LoginProvider.GITHUB)
                .providerId(String.valueOf(attributes.get("id")))
                .username(String.valueOf(attributes.get("login")))
                .profileImage(String.valueOf(attributes.get("avatar_url")))
                .name(String.valueOf(name))
                .email(AuthConstant.DUMMY_EMAIL) // String.valueOf(attributes.get("email"))
                .blog(String.valueOf(attributes.get("blog")))
                .build();
    }

    public Member toMember() {
        return Member.builder()
                .provider(this.provider)
                .providerId(this.providerId)
                .username(this.username)
                .password(AuthConstant.DUMMY_PASSWORD)
                .profileImage(this.profileImage)
                .nickname(this.name)
                .email(this.email)
                .build();
    }
}
