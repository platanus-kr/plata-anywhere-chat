package org.platanus.platachat.web.front.web;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.util.MessageAddressFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FrontController {

    private final MessageAddressFactory messageAddressFactory;

    /**
     * <h3>최상위 페이지</h3>
     *
     * @return 첫 페이지
     */
    @GetMapping
    public String front(Model model) {
        final String messageApplicationServer = messageAddressFactory.getWebServerAddress();
        model.addAttribute("messageServer", messageApplicationServer);
        return "front/front";
    }
}
