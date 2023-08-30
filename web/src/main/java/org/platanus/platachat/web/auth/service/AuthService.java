package org.platanus.platachat.web.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.dto.MemberLoginRequestDto;
import org.platanus.platachat.web.member.model.Member;

public interface AuthService {
    SessionMemberDto authorizationSession(HttpServletRequest request, HttpSession session, MemberLoginRequestDto memberLoginRequestDto);
}
