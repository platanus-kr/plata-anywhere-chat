package org.platanus.platachat.web.member.service;

import lombok.RequiredArgsConstructor;

import org.platanus.platachat.web.constants.MemberConstant;
import org.platanus.platachat.web.member.dto.MemberJoinRequest;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Member join(MemberJoinRequest memberJoinRequest) {
        String encodedPassword = passwordEncoder.encode(memberJoinRequest.password());
        Member member = validateUser(memberJoinRequest, encodedPassword);
        memberRepository.save(member);
        return member;
    }

    private Member validateUser(MemberJoinRequest dto, String encodedPassword) throws IllegalArgumentException {
        Member joinRequest = dto.toMember(encodedPassword);
        Optional<Member> findUserOpt = memberRepository.findByUsername(joinRequest.getUsername());
        if (findUserOpt.isPresent()) {
            Member findUser = findUserOpt.get();
            if (dto.email().equals(findUser.getEmail())) {
                throw new IllegalArgumentException(MemberConstant.MEMBER_DUPLICATED_EMAIL_MESSAGE);
            }
            if (dto.username().equals(findUser.getUsername())) {
                throw new IllegalArgumentException(MemberConstant.MEMBER_DUPLICATED_USERID_MESSAGE);
            }
            if (dto.nickname().equals(findUser.getNickname())) {
                throw new IllegalArgumentException(MemberConstant.MEMBER_DUPLICATED_NICKNAME_MESSAGE);
            }
            if (findUser.getDeleted()) {
                throw new IllegalArgumentException(MemberConstant.MEMBER_ALREADY_REVOKE_USER_MESSAGE);
            }
        }
        return joinRequest;
    }

    @Override
    @Deprecated(since = "테스트 다시 만들면서 반드시 제거할 것 ")
    public Member getMemberWithPassword(String username, String password) throws IllegalArgumentException {
        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(MemberConstant.MEMBER_NOT_FOUND_MESSAGE));
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new IllegalArgumentException(MemberConstant.MEMBER_NOT_MATCH_PASSWORD_MESSAGE);
        }
        return findMember;
    }

    @Override
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(MemberConstant.MEMBER_NOT_FOUND_MESSAGE));
    }

    @Override
    public Member findByProviderId(String providerId) {
        return memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException(MemberConstant.MEMBER_NOT_FOUND_MESSAGE));
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(MemberConstant.MEMBER_NOT_FOUND_MESSAGE));
    }

    @Override
    public Member findById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MemberConstant.MEMBER_NOT_FOUND_MESSAGE));
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public boolean leave(Member member) {
        return false;
    }
}
