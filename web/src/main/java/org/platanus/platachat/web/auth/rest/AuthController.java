package org.platanus.platachat.web.auth.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveRequest;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveResponse;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.auth.exception.CustomAuthException;
import org.platanus.platachat.web.auth.service.AuthService;
import org.platanus.platachat.web.constants.AuthConstant;
import org.platanus.platachat.web.member.dto.MemberJoinRequest;
import org.platanus.platachat.web.member.dto.MemberJoinResponse;
import org.platanus.platachat.web.auth.dto.AppLoginRequest;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final RedisSessionRepository sessionRepository;

    @GetMapping("/endpoint/check")
    public SessionMemberDto endpointTest(@HasMember SessionMemberDto member) {
        return member;
    }

    /**
     * <h3>회원 가입</h3>
     * POST /api/v1/auth/join
     *
     * @param joinRequest
     * @param request
     * @return
     */
    @PostMapping("/join")
    public MemberJoinResponse join(@RequestBody MemberJoinRequest joinRequest, HttpServletRequest request) {
        Member member;
        try {
            member = memberService.join(joinRequest);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        return MemberJoinResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }

    /**
     * <h3>앱 자체 로그인</h3>
     * POST /api/v1/auth/login
     */
    @PostMapping("/login")
    public SessionMemberDto appLogin(@RequestBody @Valid AppLoginRequest dto,
                                     HttpServletRequest request,
                                     HttpSession session) {
        return authService.login(request, session, dto);
    }

    /**
     * <h3>채팅방 입장시 세션 유효성 검증</h3>
     * POST /api/v1/auth/validate <br/>
     * message 에서 호출.
     *
     * @param retrieveRequestDto {@link AuthValidRetrieveRequest}
     * @return {@link AuthValidRetrieveResponse}
     */
    @PostMapping("/validate")
    public AuthValidRetrieveResponse validate(@RequestBody AuthValidRetrieveRequest retrieveRequestDto) {
        AuthValidRetrieveResponse responseDto = null;

        // 유효성 검증 요청 DTO가 null이거나 필수값이 비어있는 경우
        if (retrieveRequestDto == null ||
                StringUtils.isBlank(retrieveRequestDto.getSessionId()) ||
                StringUtils.isBlank(retrieveRequestDto.getRoomId())) {
            AuthValidRetrieveResponse res = AuthValidRetrieveResponse.builder()
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
     * <h3>로그인 상태이고 채팅방 입장이 가능한지 여부 확인</h3>
     *
     * @param retrieveRequestDto {@link AuthValidRetrieveRequest}
     * @param sessionMemberDto   {@link SessionMemberDto}
     * @param authentication     {@link Authentication}
     * @param responseDto        {@link AuthValidRetrieveResponse}
     * @return {@link AuthValidRetrieveResponse}
     */
    private AuthValidRetrieveResponse getAuthRetrieveResponseDto(AuthValidRetrieveRequest retrieveRequestDto,
                                                                 SessionMemberDto sessionMemberDto,
                                                                 Authentication authentication,
                                                                 AuthValidRetrieveResponse responseDto) {
        // 로그인 상태이고 채팅방 입장이 가능한 경우
        if (isValidate(retrieveRequestDto, sessionMemberDto, authentication) && isAdmission(retrieveRequestDto)) {
            responseDto = AuthValidRetrieveResponse.builder()
                    .nickname(sessionMemberDto.getNickname())
                    .isAdmission(true)
                    .isLogin(true)
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();
        }

        // 로그인 상태이지만 채팅방 입장이 불가능한 경우
        if (isValidate(retrieveRequestDto, sessionMemberDto, authentication) && !isAdmission(retrieveRequestDto)) {
            responseDto = AuthValidRetrieveResponse.builder()
                    .isAdmission(false)
                    .isLogin(true)
                    .message(AuthConstant.NOT_ADMISSION_MESSAGE)
                    .build();
            throw new CustomAuthException(responseDto);
        }

        // 로그인 상태가 아닌 경우
        if (!isValidate(retrieveRequestDto, sessionMemberDto, authentication)) {
            responseDto = AuthValidRetrieveResponse.builder()
                    .isAdmission(false)
                    .isLogin(false)
                    .message(AuthConstant.NOT_ADMISSION_MESSAGE) // 로그인 상태가 아닙니다.
                    .build();
            throw new CustomAuthException(responseDto);
        }
        return responseDto;
    }

    /**
     * <h3>채팅방 입장이 가능한지 여부 확인</h3>
     *
     * @param retrieveRequestDto {@link AuthValidRetrieveRequest}
     * @return 채팅방 입장 가능 여부
     */
    private boolean isAdmission(AuthValidRetrieveRequest retrieveRequestDto) {
        return true;
    }

    /**
     * <h3>요청과 세션 조회 결과의 유효성 검증</h3>
     *
     * @param retrieveRequestDto {@link AuthValidRetrieveRequest}
     * @param sessionMemberDto   {@link SessionMemberDto}
     * @param authentication     {@link Authentication}
     * @return 유효성 검증 여부
     */
    private boolean isValidate(AuthValidRetrieveRequest retrieveRequestDto,
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
