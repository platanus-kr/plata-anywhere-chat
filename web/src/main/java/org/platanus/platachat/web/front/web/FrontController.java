package org.platanus.platachat.web.front.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @Value("${plataanywherechat.message.application.location}")
    private String messageAppServer;

    /**
     * <h3>최상위 페이지</h3>
     *
     * @return 첫 페이지
     */
    @GetMapping
    public String front(Model model) {
        model.addAttribute("messageServer", messageAppServer);
        return "front/front";
    }
}
