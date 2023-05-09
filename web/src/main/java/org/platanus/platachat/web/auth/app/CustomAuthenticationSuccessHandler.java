package org.platanus.platachat.web.auth.app;

import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Spring Security 를 위한 Context 예절 주입기
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Member) {
            Member m = (Member) principal;
//        if (principal instanceof SessionMemberDto) {
//            SessionMemberDto m = (SessionMemberDto) principal;
//            SessionMemberDto build = SessionMemberDto.builder()
//                    .id(m.getId())
//                    .username(m.getUsername())
//                    .nickname(m.getNickname())
//                    .email(m.getEmail())
//                    .profileImage(m.getProfileImage())
//                    .htmlUrl(m.getHtmlUrl())
//                    .appRole(m.getAppRole())
//                    .token(session.getId())
//                    .build();
            // SessionMemberDto 객체 생성 및 저장
//            session.setAttribute("member", build);
            session.setAttribute("member", new SessionMemberDto(m, session.getId()));
        }
    }
}
