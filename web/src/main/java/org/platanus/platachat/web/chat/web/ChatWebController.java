package org.platanus.platachat.web.chat.web;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatWebController {

    @Value("${plataanywherechat.message.application.location}")
    private String messageAppServer;

    private final RoomService roomService;

    @GetMapping("/lobby")
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
        return "chat/lobby";
    }

    @GetMapping("/room/{roomId}")
    public String chatInRoom(Model model,
                             @PathVariable(required = true) String roomId,
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

        try {
            roomService.validateChatSessionAsPublic(roomId, member);
        } catch (IllegalArgumentException e) {
            model.addAttribute("isChatSessionValid", false);
            model.addAttribute("sessionValidErrorMessage", e.getMessage());
            return "chat/lobby";
        }

        model.addAttribute("isChatSessionValid", true);
        model.addAttribute("sessionValidErrorMessage", "none");
        return "chat/room";
    }

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
