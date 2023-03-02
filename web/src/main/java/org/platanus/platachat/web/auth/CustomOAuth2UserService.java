package org.platanus.platachat.web.auth;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.constants.AuthConstant;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

/**
 * OAuth2 가입을 위한 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession session;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        CustomOAuth2MemberDto attributes = CustomOAuth2MemberDto.ofGitHub(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        if (isDeletedUser(attributes)) {
            throw new OAuth2AuthenticationException("이미 탈퇴된 회원 입니다."); // TODO : 적절한 에러 처리하기
        }
        Member m = upsert(attributes);

        session.setAttribute("oAuthToken", userRequest.getAccessToken().getTokenValue());
        session.setAttribute("member", new SessionMember(m.getId(), m.getProvider(), m.getUsername(), m.getProfileImage(), m.getHtmlUrl(), m.getNickname(), m.getEmail(), m.getAppRole(), userRequest.getAccessToken().getTokenValue()));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(m.getAppRole().getKey())), oAuth2User.getAttributes(), attributes.getNameAttributeKey());
    }

    private boolean isDeletedUser(CustomOAuth2MemberDto m) {
        Optional<Member> findMember = memberRepository.findByProviderId(m.getProviderId());
        if (findMember.isPresent() && findMember.get().getDeleted()) {
            return true;
        }
        if (findMember.isEmpty()){
            return false;
        }
        return false;
    }

    private Member upsert(CustomOAuth2MemberDto m) {
        Member buildMember = Member.builder().username(m.getUsername()).password(AuthConstant.DUMMY_PASSWORD).nickname(m.getName()).providerId(m.getProviderId()).provider(m.getProvider()).profileImage(m.getProfileImage()).email(m.getEmail()).htmlUrl(m.getBlog()).deleted(false)
                .appRole(AppRole.ROLE_USER)
                .build();

        Member findMember = memberRepository.findByProviderId(buildMember.getProviderId()).map(e -> e.update(buildMember)).orElse(buildMember);
        if (findMember == null) {
            Member defaultMember = m.toMember();
        }

        return memberRepository.save(findMember);
    }
}
