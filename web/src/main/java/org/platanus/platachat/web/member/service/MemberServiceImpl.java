package org.platanus.platachat.web.member.service;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.member.dto.MemberJoinRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    public final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Member join(MemberJoinRequestDto memberJoinRequestDto) {
        memberJoinRequestDto.setEncodedPassword(passwordEncoder.encode(memberJoinRequestDto.getPassword()));
        Member member = validateUser(memberJoinRequestDto);
        memberRepository.save(member);
        return member;
    }

    private Member validateUser(MemberJoinRequestDto dto) throws IllegalArgumentException {
        Member joinRequest = dto.toMember();
        Optional<Member> findUserOpt = memberRepository.findByUsername(joinRequest.getUsername());
        if (findUserOpt.isPresent()) {
            Member findUser = findUserOpt.get();
            if (dto.getEmail().equals(findUser.getEmail())) {
                throw new IllegalArgumentException("중복된 이메일 입니다.");
            }
            if (dto.getUsername().equals(findUser.getUsername())) {
                throw new IllegalArgumentException("중복된 아이디 입니다.");
            }
            if (dto.getNickname().equals(findUser.getNickname())) {
                throw new IllegalArgumentException("중복된 닉네임 입니다.");
            }
            if (findUser.getDeleted()) {
                throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
            }
        }
        return joinRequest;
    }

    @Override
    public Member login(String username, String password) throws IllegalArgumentException {
        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다."));
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다");
        }
        return findMember;
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
