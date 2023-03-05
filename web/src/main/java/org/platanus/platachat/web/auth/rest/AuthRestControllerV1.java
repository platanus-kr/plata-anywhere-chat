package org.platanus.platachat.web.auth.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.dto.MemberJoinRequestDto;
import org.platanus.platachat.web.member.dto.MemberJoinResponseDto;
import org.platanus.platachat.web.member.dto.MemberLoginRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    /**
     * 회원가입
     *
     * @param joinRequest
     * @param request
     * @return
     */
    @PostMapping
    public MemberJoinResponseDto join(@RequestBody(required = true) MemberJoinRequestDto joinRequest, HttpServletRequest request) {
        Member member;
        try {
            member = memberService.join(joinRequest);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        return MemberJoinResponseDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }

    /**
     * 앱 자체 로그인
     *
     * @param dto
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/login")
    public SessionMemberDto appLogin(@RequestBody MemberLoginRequestDto dto,
                                     HttpServletRequest request,
                                     HttpSession session) {
        Member member;
        try {
            member = memberService.login(dto.getUsername(), dto.getPassword());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        session.setAttribute("member", new SessionMemberDto(member, request.getSession().getId()));

        return new SessionMemberDto(member, session.getId());
    }


}
