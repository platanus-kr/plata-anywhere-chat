package org.platanus.platachat.web.chat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatWebController {
	
	/**
	 * 채팅 테스트용 컨트롤러
	 * @return thymeleaf template
	 */
	@GetMapping("/test")
	public String chatTest() {
		return "chat_test";
	}
}
