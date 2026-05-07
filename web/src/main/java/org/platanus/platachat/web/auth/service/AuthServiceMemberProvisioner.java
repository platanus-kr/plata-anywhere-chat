package org.platanus.platachat.web.auth.service;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.dto.AuthServiceTokenClaims;
import org.platanus.platachat.web.auth.dto.AuthProvider;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceMemberProvisioner {

    private final MemberRepository memberRepository;

    public Member findOrCreate(AuthServiceTokenClaims claims) {
        return memberRepository.findById(claims.userId())
                .orElseGet(() -> memberRepository.save(new Member(
                        claims.userId(),
                        claims.userId(),
                        AuthProvider.AUTH_SERVICE,
                        username(claims.userId()),
                        "auth-service-managed",
                        nickname(claims),
                        null,
                        null,
                        email(claims),
                        false,
                        claims.appRole(),
                        LocalDateTime.now()
                )));
    }

    private String username(String userId) {
        return "auth-service-" + userId;
    }

    private String nickname(AuthServiceTokenClaims claims) {
        return username(claims.userId());
    }

    private String email(AuthServiceTokenClaims claims) {
        if (StringUtils.hasText(claims.email())) {
            return claims.email();
        }
        return claims.userId() + "@auth-service.invalid";
    }
}
