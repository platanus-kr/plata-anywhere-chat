package org.platanus.platachat.web.auth.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthWebController {

    /**
     * 미구현
     * @return
     */
    @GetMapping("/join")
    public String join() {
        return "member_join";
    }
}
