package org.platanus.platachat.web.member.service;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    public final MemberRepository memberRepository;
    
    @Override
    public Member join(Member member) {
        // 일반회원가입 다음 파트 구현
        // 검색 -> 유니크 맞춰보기
        // 탈퇴회원 여부 -> 탈퇴회원일시 가입 불가
        return null;
    }
    
    @Override
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다."));
    }
    
    @Override
    public Member findByProviderId(String providerId) {
        return memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다."));
    }
    
    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다."));
    }
    
    @Override
    public boolean leave(Member member) {
        return false;
    }
}
