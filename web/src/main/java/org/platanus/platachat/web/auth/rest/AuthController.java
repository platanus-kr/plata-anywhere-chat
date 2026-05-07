package org.platanus.platachat.web.auth.rest;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.AuthServiceMemberDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth-service")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/me")
    public AuthServiceMemberDto me(@HasMember AuthServiceMemberDto member) {
        return member;
    }
}
