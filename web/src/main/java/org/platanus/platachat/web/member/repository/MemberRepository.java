package org.platanus.platachat.web.member.repository;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.repository.jpa.MemberJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MemberJpaRepository {
    Optional<Member> findByProviderId(String id);

    Optional<Member> findByUsername(String username);
    
    Optional<Member> findByEmail(String email);
}
