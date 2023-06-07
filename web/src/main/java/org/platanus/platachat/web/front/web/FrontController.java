package org.platanus.platachat.web.front.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping
    public String front() {
        return "front/front";
    }
}
