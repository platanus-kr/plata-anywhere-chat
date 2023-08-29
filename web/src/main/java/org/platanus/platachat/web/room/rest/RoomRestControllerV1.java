package org.platanus.platachat.web.room.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.constants.RoomConstant;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomCreateResponseDto;
import org.platanus.platachat.web.room.dto.RoomMemberResponseDto;
import org.platanus.platachat.web.room.dto.RoomRetrieveResponseDto;
import org.platanus.platachat.web.room.dto.RoomStatusRequestDto;
import org.platanus.platachat.web.room.dto.RoomStatusResponseDto;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.repository.exception.RoomException;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomRestControllerV1 {

    private final MemberService memberService;
    private final RoomService roomService;

    /**
     * <h3>채팅방 생성</h3>
     * POST /api/v1/room
     *
     * @param requestDto       {@link RoomCreateRequestDto}
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link RoomCreateResponseDto}
     */
    @PostMapping
    public RoomCreateResponseDto create(@RequestBody RoomCreateRequestDto requestDto,
                                        @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }

        return createChatRoom(requestDto, sessionMemberDto);
    }

    /**
     * <h3>채팅방 생성 : 서비스 호출부</h3>
     *
     * @param requestDto       {@link RoomCreateRequestDto}
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link RoomCreateResponseDto}
     */
    private RoomCreateResponseDto createChatRoom(RoomCreateRequestDto requestDto, SessionMemberDto sessionMemberDto) {
        // 세션에서 회원 추출
        Member m = memberService.findById(sessionMemberDto.getId());

        // 채팅방 생성
        Room r = roomService.createRoom(requestDto, m);
//        Room findByR = roomService.getRoomById(r.getId(), sessionMemberDto);
        List<RoomMember> participates = r.getParticipates();

        return RoomCreateResponseDto.builder()
                .roomId(r.getId())
                .participates(List.of(RoomMemberResponseDto.from(m, participates.stream().findAny().orElseThrow())))
                .build();
    }

    /**
     * <h3>채팅방 정보 수정</h3>
     * 공개여부, 종료여부, 채팅방 메타정보.<br/>
     * PUT /api/v1/room/{roomId}
     *
     * @param roomId           변경할 채팅방 식별자
     * @param requestDto       {@link RoomStatusRequestDto}
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link RoomStatusResponseDto}
     */
    @PutMapping("/{roomId}")
    public RoomStatusResponseDto modify(@PathVariable("roomId") String roomId,
                                        @RequestBody RoomStatusRequestDto requestDto,
                                        @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        return modifyChatRoom(roomId, requestDto, sessionMemberDto);
    }

    /**
     * <h3>채팅방 정보 수정 : 서비스 호출부</h3>
     *
     * @param roomId           변경할 채팅방 식별자
     * @param requestDto       {@link RoomStatusRequestDto}
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link RoomStatusResponseDto}
     */

    private RoomStatusResponseDto modifyChatRoom(String roomId,
                                                 RoomStatusRequestDto requestDto,
                                                 SessionMemberDto sessionMemberDto) {
        try {
            roomService.changeRoomInformation(roomId, requestDto, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return RoomStatusResponseDto.builder()
                .key("ok")
                .message(RoomConstant.ROOM_INFORMATION_CHANGE_OK_MESSAGE)
                .build();
    }

    /**
     * <h3>채팅방 방장 변경</h3>
     * PUT /api/v1/room/{roomId}/owner
     *
     * @param roomId           변경할 채팅방 식별자
     * @param requestDto       {@link RoomStatusRequestDto}
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link RoomStatusResponseDto}
     */
    @PutMapping("/{roomId}/owner")
    public RoomStatusResponseDto modifyOwner(@PathVariable("roomId") String roomId,
                                             @RequestBody RoomStatusRequestDto requestDto,
                                             @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        return modifyChatRoomOwner(roomId, requestDto, sessionMemberDto);
    }

    /**
     * <h3>채팅방 방장 변경 : 서비스 호출부</h3>
     *
     * @param roomId           변경할 채팅방 식별자
     * @param requestDto       {@link RoomStatusRequestDto}
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link RoomStatusResponseDto}
     */
    private RoomStatusResponseDto modifyChatRoomOwner(String roomId,
                                                      RoomStatusRequestDto requestDto,
                                                      SessionMemberDto sessionMemberDto) {
        try {
            roomService.changeRoomOwner(roomId, requestDto, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return RoomStatusResponseDto.builder()
                .key("ok")
                .message(RoomConstant.ROOM_INFORMATION_CHANGE_OK_MESSAGE)
                .build();
    }

    /**
     * <h3>개별 채팅방 정보 조회</h3>
     * GET /api/v1/room/{roomId}
     *
     * @param roomId           조회할 채팅방 식별자
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link RoomRetrieveResponseDto}
     */
    @GetMapping("/{roomId}")
    public RoomRetrieveResponseDto retrieve(@PathVariable("roomId") String roomId,
                                            @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        Room room;
        try {
            room = roomService.getRoomById(roomId, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return RoomRetrieveResponseDto.withEntityGraphFrom(room);
    }

    /**
     * <h3>공개된 채팅방 목록</h3>
     * GET /api/v1/room/list
     *
     * @param sessionMemberDto {@link SessionMemberDto}
     * @param page             페이지
     * @return {@link Page}<{@link RoomRetrieveResponseDto}>
     */
    @GetMapping("/list")
    public Page<RoomRetrieveResponseDto> retrievePublicRooms(@HasMember SessionMemberDto sessionMemberDto,
                                                             @RequestParam(value = "page", defaultValue = "1") int page) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        if (page > 0) {
            page -= 1;
        }
        Page<Room> publicRooms = roomService.getRoomsByVisibleAsPaging(page);
        return RoomRetrieveResponseDto.from(publicRooms);
    }

    /**
     * <h3>내가 방장인 채팅방 목록</h3>
     * GET /api/v1/room/own/list
     *
     * @param sessionMemberDto {@link SessionMemberDto}
     * @param page             페이지
     * @return {@link Page}<{@link RoomRetrieveResponseDto}>
     */
    @GetMapping("/own/list")
    public Page<RoomRetrieveResponseDto> retrieveMyRooms(@HasMember SessionMemberDto sessionMemberDto,
                                                         @RequestParam(value = "page", defaultValue = "1") int page) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        if (page > 0) {
            page -= 1;
        }
        Page<Room> roomsByMemberId = roomService.getRoomsByMemberIdAsPaging(sessionMemberDto.getId(), page);
        return RoomRetrieveResponseDto.from(roomsByMemberId);
    }

    /**
     * <h3>채팅방 입장</h3>
     * POST /api/v1/room/{roomId}/join
     *
     * @param roomId           입장하는 채팅방 식별자
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link ResponseEntity}
     */
    @PostMapping("/{roomId}/join")
    public ResponseEntity<Void> joinRoom(@PathVariable("roomId") String roomId,
                                         @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        try {
            roomService.joinRoom(roomId, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * <h3>채팅방 나가기</h3>
     * POST /api/v1/room/{roomId}/exit
     *
     * @param roomId           나가는 채팅방 식별자
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link ResponseEntity}
     */
    @PostMapping("/{roomId}/exit")
    public ResponseEntity<Void> exitRoom(@PathVariable("roomId") String roomId,
                                         @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        try {
            roomService.exitRoom(roomId, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * <h3>채팅방 종료</h3>
     * DELETE /api/v1/room/{roomId}
     *
     * @param roomId           종료하는 채팅방 식별자
     * @param sessionMemberDto {@link SessionMemberDto}
     * @return {@link ResponseEntity}
     */
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> endChat(@PathVariable("roomId") String roomId,
                                        @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_MEMBER_VALIDATE_FAILED_MESSAGE);
        }
        try {
            roomService.endChat(roomId, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
