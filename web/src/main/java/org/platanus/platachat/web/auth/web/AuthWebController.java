package org.platanus.platachat.web.auth.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
public class AuthWebController {

    @GetMapping("/join")
    public String join() {
        return "member_join";
    }
}
