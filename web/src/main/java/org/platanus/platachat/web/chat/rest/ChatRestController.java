package org.platanus.platachat.web.chat.rest;

import java.util.ArrayList;
import java.util.List;

import org.platanus.platachat.web.chat.model.MessagePayload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatlog")
public class ChatRestController {
	
	@GetMapping("/simple/{roomId}")
	public List<MessagePayload> getChatLogSimple(@PathVariable(value = "roomId") String roomId,
												 @RequestParam(value = "page", required = false) int page) {
		return new ArrayList<>();
	}
}
