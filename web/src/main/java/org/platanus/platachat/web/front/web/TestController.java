package org.platanus.platachat.web.front.web;

import org.platanus.platachat.web.auth.SessionMember;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/endpoint")
    public String endpointTest(@HasMember SessionMember member) {
        return member.toString();
    }
}
