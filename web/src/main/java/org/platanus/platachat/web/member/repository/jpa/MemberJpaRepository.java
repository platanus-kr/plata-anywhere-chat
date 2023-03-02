package org.platanus.platachat.web.member.repository.jpa;

import org.platanus.platachat.web.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderId(String id);

    Optional<Member> findByUsername(String username);
    
    Optional<Member> findByEmail(String email);
}
