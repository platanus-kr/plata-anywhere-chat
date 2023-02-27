package org.platanus.platachat.web.member.repository.jpa;

import java.util.Optional;

import org.platanus.platachat.web.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
