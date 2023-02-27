package org.platanus.platachat.web.MemberTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 1
 */
@DataJpaTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원_가입() {
        final Member member = Member.builder()
                .username("testUser")
                .password("testPassword")
                .nickname("testNickname")
                .email("testUser@gmail.com")
                .appRole(null)
                .deleted(Boolean.FALSE)
                .lastActivated(LocalDateTime.now())
                .build();

        memberRepository.save(member);

        Member findByUsername = memberRepository.findByUsername("testUser").orElseGet(Assertions::fail);
        System.out.println(findByUsername);
        assertThat(findByUsername.getUsername()).isEqualTo("testUser");
    }

    @Test
    void 회원_수정() {
        final Member member = Member.builder()
                .username("testUser")
                .password("testPassword")
                .nickname("testNickname")
                .email("testUser@gmail.com")
                .appRole(null)
                .deleted(Boolean.FALSE)
                .lastActivated(LocalDateTime.now())
                .build();

        memberRepository.save(member);

        Member findByUsername = memberRepository.findByUsername("testUser").orElseGet(Assertions::fail);
        System.out.println(findByUsername);
        Member modifyMember = Member.builder()
                .id(findByUsername.getId())
                .username(findByUsername.getUsername())
                .password(findByUsername.getPassword())
                .nickname("newNickname")
                .appRole(findByUsername.getAppRole())
                .deleted(findByUsername.getDeleted())
                .lastActivated(findByUsername.getLastActivated())
                .email(findByUsername.getEmail())
                .created(findByUsername.getCreated())
                .updated(LocalDateTime.now())
                .build();

        Member save = memberRepository.save(modifyMember);

        System.out.println(save);
        assertThat(save.getNickname()).isEqualTo("newNickname");


    }
}
