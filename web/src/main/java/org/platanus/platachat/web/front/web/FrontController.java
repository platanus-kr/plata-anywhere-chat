package org.platanus.platachat.web.front.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FrontController {
    private static final String PAC_MESSAGE_HOSTNAME = "plataanywherechat.message.application.host";
    private static final String PAC_MESSAGE_PORT = "plataanywherechat.message.application.port";
    private final Environment env;

    /**
     * <h3>최상위 페이지</h3>
     *
     * @return 첫 페이지
     */
    @GetMapping
    public String front(Model model) {
        final String messageApplicationServer = env.getProperty(PAC_MESSAGE_HOSTNAME) + ":" + env.getProperty(PAC_MESSAGE_PORT);
        model.addAttribute("messageServer", messageApplicationServer);
        return "front/front";
    }
}
