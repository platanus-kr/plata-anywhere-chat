package org.platanus.platachat.web.member.service;

import org.platanus.platachat.web.member.dto.MemberJoinRequest;
import org.platanus.platachat.web.member.model.Member;

import java.util.List;

public interface MemberService {

    /**
     * 회원 가입
     *
     * @param memberJoinRequest 가입하고자 하는 회원 모델
     * @return 가입완료된 회원
     */

    Member join(MemberJoinRequest memberJoinRequest);

    /**
     * 회원 찾기
     *
     * @param username 회원아이디
     * @return 검색완료 회원
     */
    Member findByUsername(String username);

    /**
     * 회원 찾기
     *
     * @param providerId OAuth2 고유번호
     * @return 검색완료 회원
     */
    Member findByProviderId(String providerId);

    /**
     * 회원 찾기
     *
     * @param email 이메일
     * @return 검색완료 회원
     */
    Member findByEmail(String email);

    Member findById(String id);

    List<Member> findAll();

    /**
     * 회원 탈퇴
     *
     * @param member 탈퇴하고자 하는 회원 모델
     * @return 탈퇴여부
     */
    boolean leave(Member member);


    Member getMemberWithPassword(String username, String password);
}
