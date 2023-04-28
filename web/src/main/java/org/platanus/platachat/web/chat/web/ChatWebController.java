package org.platanus.platachat.web.chat.web;

import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatWebController {
	
	/**
	 * 채팅 테스트용 컨트롤러
	 * @return thymeleaf template
	 */
	@GetMapping("/simple")
	public String chatTest() {
		return "simple_room";
	}
	
	@GetMapping("/simple_haslogin")
	public String chatTestHasLogin(Model model, @HasMember SessionMemberDto member) {
		model.addAttribute("member", member);
		return "simple_room_haslogin";
	}
}
