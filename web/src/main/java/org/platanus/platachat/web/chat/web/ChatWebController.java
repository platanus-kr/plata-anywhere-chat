package org.platanus.platachat.web.chat.web;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.constants.RoomConstant;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

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
     * @param model   {@link Model}
     * @param member  {@link Member}
     * @param request {@link HttpServletRequest}
     * @return 채팅방 목록 페이지
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

    // TODO: 채팅방 생성 구현 필요 -> REST는 되어있다.
    @GetMapping("/room/create")
    public String createRoom(Model model,
                             @HasMember SessionMemberDto sessionMemberDto,
                             HttpServletRequest request) {
        return null;
    }

    // TODO: 채팅방 수정 구현 필요 -> REST는 되어있다.
    @GetMapping("/room/modify/{roomId}")
    public String modifyRoom(Model model,
                             @PathVariable(required = true) String roomId,
                             @HasMember SessionMemberDto sessionMemberDto,
                             HttpServletRequest request) {
        return null;
    }

    /**
     * <h3>채팅방</h3>
     * GET /chat/room/{roomId}<br/>
     * 채팅방 사용을 위한 web 컨트롤러. <br/>
     * 각 단계를 거친다</br>
     * <ol>
     *     <li>세션 분기 처리</li>
     *     <li>방 상태 확인</li>
     *     <li>회원 상태 확인</li>
     *     <li>회원을 채팅방에 추가</li>
     *     <li>채팅방 정보 바인딩 후 채팅방 view로 전달</li>
     * </ol>
     *
     * @param model            {@link Model}
     * @param roomId           채팅방 식별자
     * @param sessionMemberDto {@link SessionMemberDto}
     * @param request          {@link HttpServletRequest}
     * @return 채팅방 페이지
     */
    @GetMapping("/room/{roomId}")
    public String chatInRoom(Model model,
                             @PathVariable(required = true) String roomId,
                             @HasMember SessionMemberDto sessionMemberDto,
                             HttpServletRequest request) {

        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            model.addAttribute("isChatSessionValid", false);
            model.addAttribute("sessionValidErrorMessage", RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
            return "chat/lobby";
        }

        // 세션 분기
        String sessionId = null;
        if (sessionMemberDto.getProvider().equals(LoginProvider.WEB)) {
            sessionId = sessionMemberDto.getToken();
        }
        if (!sessionMemberDto.getProvider().equals(LoginProvider.WEB)) {
            HttpSession session = request.getSession(false);
            sessionId = session.getId();
        }

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

        model.addAttribute("pacSessionId", sessionId);
        model.addAttribute("pacSessionMember", sessionMemberDto);
        model.addAttribute("pacMessageServer", messageAppServer);
        model.addAttribute("pacRoomId", roomById.getId());
        model.addAttribute("isChatSessionValid", true);
        model.addAttribute("sessionValidErrorMessage", "none");
        return "chat/room";
    }

    /**
     * <h3>채팅 로그 목록</h3>
     * GET /chat/log
     *
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return 채팅 로그 목록 view
     */
    @GetMapping("/log")
    public String chatLogList(@HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            return "/"; //TODO: ExceptionHandler
        }
        return "chat/log_list";
    }

    /**
     * <h3>채팅 로그 상세</h3>
     * GET /chat/log/{roomId}
     *
     * @param model            {@link Model}
     * @param roomId           채팅방 식별자
     * @param sessionMemberDto {@link SessionMemberDto}
     * @param request          {@link HttpServletRequest}
     * @return 채팅 로그 상세 view
     */
    @GetMapping("/log/{roomId}")
    public String retrieveChatLog(Model model,
                                  @PathVariable String roomId,
                                  @HasMember SessionMemberDto sessionMemberDto,
                                  HttpServletRequest request) {

        // roomMember 유효성 검증
        try {
            roomService.validateRoomMemberInChat(roomId, sessionMemberDto.getId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e); // TODO: ExceptionHandler 구축
        }

        model.addAttribute("pacRoomId", roomId);

        return "chat/log_detail";
    }

    /**
     * 채팅 테스트용 컨트롤러
     *
     * @return thymeleaf template
     */
    @Deprecated
    @GetMapping("/room/random")
    public String chatRandomFront(Model model) {
        model.addAttribute("messageServer", messageAppServer);
        return "chat/simple_room";
    }


    // TODO: 채팅로그조회 구현하기
    @GetMapping("/log/simple")
    public String chatLog() {
        return "simple_chatlog";
    }


}
