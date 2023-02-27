package org.platanus.platachat.web.member.service;

import org.platanus.platachat.web.member.model.Member;
import org.springframework.stereotype.Service;

public interface MemberService {
    
    Member join(Member member);
}
