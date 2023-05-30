package org.platanus.platachat.web.chat.web;

import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chat")
public class ChatWebController {

    @Value("${plataanywherechat.message.application.location}")
    private String messageAppServer;

    /**
     * 채팅 테스트용 컨트롤러
     *
     * @return thymeleaf template
     */
    @GetMapping("/room/random")
    public String chatRandomFront(Model model) {
        model.addAttribute("messageServer", messageAppServer);
        return "chat/simple_room";
    }

    @GetMapping
    public String chatFront(Model model,
                            @HasMember SessionMemberDto member,
                            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String sessionId = null;
        if (session != null) {
            sessionId = session.getId();
        }
        if (!member.getProvider().equals(LoginProvider.WEB)) {
            model.addAttribute("pacsessionid", sessionId);
        } else {
            model.addAttribute("pacsessionid", member.getToken());
        }
        model.addAttribute("member", member);
        model.addAttribute("messageServer", messageAppServer);
        return "chat/room";
    }

    @GetMapping("/store/simple")
    public String chatLog() {
        return "simple_chatlog";
    }

    @GetMapping("/store/simple_haslogin")
    public String chatLogHasLogin(Model model,
                                  @HasMember SessionMemberDto member,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String sessionId = null;
        if (session != null) {
            sessionId = session.getId();
        }
        if (!member.getProvider().equals(LoginProvider.WEB)) {
            model.addAttribute("pacsessionid", sessionId);
        } else {
            model.addAttribute("pacsessionid", member.getToken());
        }
        model.addAttribute("member", member);
        return "simple_chatlog";
    }
}
