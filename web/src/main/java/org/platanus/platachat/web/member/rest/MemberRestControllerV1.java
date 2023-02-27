package org.platanus.platachat.web.member.rest;

import org.platanus.platachat.web.member.dto.MemberJoinDto;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberRestControllerV1 {
    
    private final MemberService memberService;
    
    @PostMapping
    public MemberJoinDto join(@RequestBody MemberJoinDto dto) {
        return null;
    }
}
