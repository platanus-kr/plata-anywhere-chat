package org.platanus.platachat.web.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.dto.MemberLoginRequestDto;

public interface AuthService {
    SessionMemberDto login(HttpServletRequest request, HttpSession session, MemberLoginRequestDto memberLoginRequestDto);
}
