package org.platanus.platachat.web.chat.web;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomMemberStatus;
import org.platanus.platachat.web.room.model.RoomRole;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatWebController {
	
	private final RoomService roomService;
	private final MemberService memberService;
	@Value("${plataanywherechat.message.application.location}")
	private String messageAppServer;

	/**
	 * <h3>채팅방 목록 </h3>
	 * GET /chat/lobby
	 *
	 * @param model
	 * @param member
	 * @param request
	 * @return
	 */
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
	
	@GetMapping("/room/create")
	public String createRoom(Model model,
							 @HasMember SessionMemberDto sessionMemberDto,
							 HttpServletRequest request) {
		return null;
	}
	
	@GetMapping("/room/modify/{roomId}")
	public String modifyRoom(Model model,
							 @PathVariable(required = true) String roomId,
							 @HasMember SessionMemberDto sessionMemberDto,
							 HttpServletRequest request) {
		return null;
	}
	
	@GetMapping("/room/{roomId}")
	public String chatInRoom(Model model,
							 @PathVariable(required = true) String roomId,
							 @HasMember SessionMemberDto sessionMemberDto,
							 HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String sessionId = null;
		if (session != null) {
			sessionId = session.getId();
		}
        
        // 세션 분기
		if (!sessionMemberDto.getProvider().equals(LoginProvider.WEB)) {
			model.addAttribute("pacsessionid", sessionId);
		} else {
			model.addAttribute("pacsessionid", sessionMemberDto.getToken());
		}
		model.addAttribute("member", sessionMemberDto);
		model.addAttribute("messageServer", messageAppServer);
		
        // 방 상태 확인
		Room roomById;
		try {
			roomById = roomService.validateChatSessionAsPublic(roomId, sessionMemberDto);
		} catch (IllegalArgumentException e) {
			model.addAttribute("isChatSessionValid", false);
			model.addAttribute("sessionValidErrorMessage", e.getMessage());
			return "chat/lobby";
		}
  
        // 회원 확인
		Member memberBySession;
		try {
			memberBySession = memberService.findById(sessionMemberDto.getId());
		} catch (IllegalArgumentException e) {
            model.addAttribute("isChatSessionValid", false);
            model.addAttribute("sessionValidErrorMessage", e.getMessage());
            return "chat/lobby";
		}
		
        // 회원을 방에 추가
		RoomMember roomMember = RoomMember.builder()
				.room(roomById)
				.role(RoomRole.MEMBER)
				.member(memberBySession)
				.joinDateTime(LocalDateTime.now())
				.status(RoomMemberStatus.ALIVE)
				.build();
		roomService.addRoomMember(roomMember);
		
		model.addAttribute("isChatSessionValid", true);
		model.addAttribute("sessionValidErrorMessage", "none");
		return "chat/room";
	}
	
	/**
	 * 채팅 테스트용 컨트롤러
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
