package org.platanus.platachat.web.MemberTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 1
 */
@DataJpaTest
public class MemberTest {
    
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원_가입(){
        final Member member = new Member(null,
                "testUser1",
                "testPassword",
                "test1",
                "testuser1@test.com",
                false,
                null,
                LocalDateTime.now());
        
        memberRepository.save(member);
    
        Member findByUsername = memberRepository.findByUsername("testUser1").orElseGet(Assertions::fail);
        //Member findByUsername = memberRepository.findByUsername("testUser1").get();
    
        System.out.println(findByUsername);
        assertThat(findByUsername.getUsername()).isEqualTo("testUser1");
    }
}
