package org.platanus.platachat.web.chat.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.constants.RoomConstant;
import org.platanus.platachat.web.message.model.MessagePayload;
import org.platanus.platachat.web.message.service.MessageService;
import org.platanus.platachat.web.room.dto.RoomsRetrieveResponse;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat/log")
@RequiredArgsConstructor
public class ChatLogController {

    private final RoomService roomService;
    private final MessageService chatService;


    /**
     * <h3>채팅방 목록 : 회원</h3>
     * GET /api/v1/chat/log
     *
     * @param sessionMemberDto {@link SessionMemberDto}
     * @param page             페이지
     * @return {@link Room} 목록
     */
    @GetMapping
    public Page<RoomsRetrieveResponse> getChatLogList(@HasMember SessionMemberDto sessionMemberDto,
                                                      @RequestParam(value = "page", defaultValue = "1") int page) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        if (page > 0) {
            page -= 1;
        }
        return roomService.getRoomDtosByMemberIdAsPaging(sessionMemberDto.getId(), page);
    }

    /**
     * <h3>채팅방 로그 상세 조회</h3>
     * GET /api/v1/chat/log/{roomId}
     *
     * @param roomId           채팅방 식별자
     * @param sessionMemberDto {@link SessionMemberDto}
     * @param page             페이지
     * @return {@link MessagePayload} 목록
     */
    @GetMapping("/{roomId}")
    public Page<MessagePayload> getChatLog(@PathVariable(value = "roomId") String roomId,
                                           @HasMember SessionMemberDto sessionMemberDto,
                                           @RequestParam(value = "page", defaultValue = "1") int page) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }

        if (page > 0) {
            page -= 1;
        }
        return chatService.getChatLogsByPaging(roomId, page);
    }
}
