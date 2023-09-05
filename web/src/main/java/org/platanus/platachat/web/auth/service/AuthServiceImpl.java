package org.platanus.platachat.web.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.auth.app.CustomUserDetailsService;
import org.platanus.platachat.web.auth.app.CustomUserDetailsUserAdaptor;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.constants.MemberConstant;
import org.platanus.platachat.web.member.dto.MemberLoginRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    /**
     * CustomAuthenticationProvider의 기능을 실제 사용한다.
     */
    @Override
    public SessionMemberDto login(HttpServletRequest request, HttpSession session, MemberLoginRequestDto dto) {

        CustomUserDetailsUserAdaptor memberContext =
                (CustomUserDetailsUserAdaptor) customUserDetailsService.loadUserByUsername(dto.getUsername());
        Member member = memberContext.getMember();

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException(MemberConstant.MEMBER_NOT_MATCH_PASSWORD_MESSAGE);
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(memberContext.getMember(), null, memberContext.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        session.setAttribute("member", new SessionMemberDto(member, request.getSession().getId()));

        return new SessionMemberDto(member, session.getId());
    }
}
