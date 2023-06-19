package org.platanus.platachat.web.front.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    /**
     * <h3>최상위 페이지</h3>
     *
     * @return 첫 페이지
     */
    @GetMapping
    public String front() {
        return "front/front";
    }
}
