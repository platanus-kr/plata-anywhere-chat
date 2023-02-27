package org.platanus.platachat.web.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberWebController {
    
    @GetMapping("/join")
    public String join() {
        return "member_join";
    }
}
