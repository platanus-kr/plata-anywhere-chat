package org.platanus.platachat.web.auth.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveRequestDto;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveResponseDto;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.auth.exception.CustomAuthException;
import org.platanus.platachat.web.constants.AuthConstant;
import org.platanus.platachat.web.member.dto.MemberJoinRequestDto;
import org.platanus.platachat.web.member.dto.MemberJoinResponseDto;
import org.platanus.platachat.web.member.dto.MemberLoginRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisSessionRepository;
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
    private final RedisSessionRepository sessionRepository;

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

    /**
     * 채팅방 입장시 세션 유효성 검증
     *
     * @param retrieveRequestDto 유효성 검증 요청 DTO
     * @return 유효성 검증 응답 DTO
     */
    @PostMapping("/validate")
    public AuthValidRetrieveResponseDto validate(@RequestBody AuthValidRetrieveRequestDto retrieveRequestDto) {
        AuthValidRetrieveResponseDto responseDto = null;

        // 유효성 검증 요청 DTO가 null이거나 필수값이 비어있는 경우
        if (retrieveRequestDto == null ||
                StringUtils.isBlank(retrieveRequestDto.getSessionId()) ||
                StringUtils.isBlank(retrieveRequestDto.getRoomId())) {
            AuthValidRetrieveResponseDto res = AuthValidRetrieveResponseDto.builder()
                    .isAdmission(false)
                    .isLogin(false)
                    .message(AuthConstant.INVALID_REQUEST_MESSAGE)
                    .build();
            throw new CustomAuthException(res);
        }

        SessionMemberDto sessionMemberDto;
        SecurityContextImpl securityContext;
        Authentication authentication;
        try {
            Session findSession = sessionRepository.findById(retrieveRequestDto.getSessionId());
            sessionMemberDto = findSession.getAttribute("member");
            securityContext = findSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            authentication = securityContext.getAuthentication();
        } catch (CustomAuthException e) {
            throw new CustomAuthException(e.getResponseDto());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomAuthException(e);
        }

        responseDto = getAuthRetrieveResponseDto(retrieveRequestDto, sessionMemberDto, authentication, responseDto);
        log.info(responseDto.toString());
        return responseDto;

    }

    /**
     * 로그인 상태이고 채팅방 입장이 가능한지 여부 확인
     *
     * @param retrieveRequestDto 유효성 검증 요청 DTO
     * @param sessionMemberDto   세션 멤버 DTO
     * @param authentication     인증 객체
     * @param responseDto        유효성 검증 응답 DTO
     * @return 유효성 검증 응답 DTO
     */
    private AuthValidRetrieveResponseDto getAuthRetrieveResponseDto(AuthValidRetrieveRequestDto retrieveRequestDto,
                                                                    SessionMemberDto sessionMemberDto,
                                                                    Authentication authentication,
                                                                    AuthValidRetrieveResponseDto responseDto) {
        // 로그인 상태이고 채팅방 입장이 가능한 경우
        if (isValidate(retrieveRequestDto, sessionMemberDto, authentication) && isAdmission(retrieveRequestDto)) {
            responseDto = AuthValidRetrieveResponseDto.builder()
                    .nickname(sessionMemberDto.getNickname())
                    .isAdmission(true)
                    .isLogin(true)
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();
        }

        // 로그인 상태이지만 채팅방 입장이 불가능한 경우
        if (isValidate(retrieveRequestDto, sessionMemberDto, authentication) && !isAdmission(retrieveRequestDto)) {
            responseDto = AuthValidRetrieveResponseDto.builder()
                    .isAdmission(false)
                    .isLogin(true)
                    .message(AuthConstant.NOT_ADMISSION_MESSAGE)
                    .build();
            throw new CustomAuthException(responseDto);
        }

        // 로그인 상태가 아닌 경우
        if (!isValidate(retrieveRequestDto, sessionMemberDto, authentication)) {
            responseDto = AuthValidRetrieveResponseDto.builder()
                    .isAdmission(false)
                    .isLogin(false)
                    .message(AuthConstant.NOT_ADMISSION_MESSAGE) // 로그인 상태가 아닙니다.
                    .build();
            throw new CustomAuthException(responseDto);
        }
        return responseDto;
    }

    /**
     * 채팅방 입장이 가능한지 여부 확인
     *
     * @param retrieveRequestDto 유효성 검증 요청 DTO
     * @return 채팅방 입장 가능 여부
     */
    private boolean isAdmission(AuthValidRetrieveRequestDto retrieveRequestDto) {
        return true;
    }

    /**
     * 요청과 세션 조회 결과의 유효성 검증
     *
     * @param retrieveRequestDto 유효성 검증 요청 DTO
     * @param sessionMemberDto   세션 멤버 DTO
     * @param authentication     인증 객체
     * @return 유효성 검증 여부
     */
    private boolean isValidate(AuthValidRetrieveRequestDto retrieveRequestDto,
                               SessionMemberDto sessionMemberDto,
                               Authentication authentication) {
        if (sessionMemberDto == null || authentication == null) {
            return false;
        }
        // OAuth 벤더의 토큰 유효성 검증은 하지 않는다. 어짜피 벤더에서 물림.
        if (sessionMemberDto.getProvider().equals(LoginProvider.WEB) &&
                !StringUtils.equals(retrieveRequestDto.getSessionId(), sessionMemberDto.getToken())) {
            return false;
        }
        if (!authentication.isAuthenticated()) {
            return false;
        }
        return true;
    }


}
