package org.platanus.platachat.web.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.dto.MemberLoginRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationProvider authenticationProvider;
    //    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    @Override
    public SessionMemberDto authorizationSession(HttpServletRequest request, HttpSession session, MemberLoginRequestDto dto) {

        // TODO: 여기서 AuthenticationManager, UserDetailService 다 호출해서 해결하기.
        Member member;
        try {
            member = memberService.login(dto.getUsername(), dto.getPassword());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        Authentication authentication = getAuthentication(member.getUsername(), member.getPassword());


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
//        Authentication authentication = authenticationProvider.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
//        SecurityContextHolder.getContext().setAuthentication(token);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        session.setAttribute("member", new SessionMemberDto(member, request.getSession().getId()));
        return new SessionMemberDto(member, session.getId());
    }
}
