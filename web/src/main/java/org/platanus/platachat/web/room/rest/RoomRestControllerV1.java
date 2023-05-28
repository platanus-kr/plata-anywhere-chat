package org.platanus.platachat.web.room.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.dto.*;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.repository.exception.RoomException;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomRestControllerV1 {

    private final MemberService memberService;
    private final RoomService roomService;

    final String CHANGE_OK_MESSAGE = "변경이 완료 되었습니다.";

    /**
     * 채팅방 생성
     * POST /api/v1/room
     *
     * @param dto 채팅방 생성 요청 DTO
     * @param sessionMemberDto 인증
     * @return 채팅방 생성 응답 DTO
     */
    @PostMapping
    public RoomCreateResponseDto create(@RequestBody RoomCreateRequestDto dto,
                                        @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }

        return createChatRoom(dto, sessionMemberDto);
    }

    private RoomCreateResponseDto createChatRoom(RoomCreateRequestDto roomReqDto, SessionMemberDto smDto) {
        // 세션에서 회원 추출
        Member m = memberService.findById(smDto.getId());

        // 채팅방 생성
        Room r = roomService.createRoom(roomReqDto, m);

        return RoomCreateResponseDto.builder()
                .roomId(r.getId())
                .participates(List.of(RoomMemberResponseDto.from(m, r.getParticipates().stream().findAny().orElseThrow())))
                .build();
    }

    /**
     * 방의 상태를 변경 <br />
     * 공개여부, 종료여부, 채팅방 메타정보.
     * PUT /api/v1/room/{roomId}
     *
     * @param dto 변경할 방의 정보
     * @param sessionMemberDto 인증
     * @return 변경 응답 DTO
     */
    @PutMapping("/{roomId}")
    public RoomStatusResponseDto modify(@PathVariable("roomId") String roomId,
                                        @RequestBody RoomStatusRequestDto dto,
                                        @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        return modifyChatRoom(roomId, dto, sessionMemberDto);
    }

    private RoomStatusResponseDto modifyChatRoom(String roomId,
                                                 RoomStatusRequestDto requestDto,
                                                 SessionMemberDto smDto) {
        try {
            roomService.changeRoomInformation(roomId, requestDto, smDto);
        } catch (IllegalArgumentException e){
            throw new RoomException(e.getMessage());
        }
        return RoomStatusResponseDto.builder()
                .key("ok")
                .message(CHANGE_OK_MESSAGE)
                .build();
    }

    /**
     * 채팅방 정보 조회
     * GET /api/v1/room/{roomId}
     *
     * @param roomId
     * @param sessionMemberDto
     * @return
     */
    @GetMapping("/{roomId}")
    public RoomRetrieveResponseDto retrieve(@PathVariable("roomId") String roomId,
                                            @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        Room room;
        try {
            room = roomService.getRoomById(roomId, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return RoomRetrieveResponseDto.from(room);
    }

    /**
     * 공개된 채팅방 목록
     * GET /api/v1/room/list
     *
     * @param sessionMemberDto 인증
     * @param page 페이지
     * @return 공개된 채팅방 페이징 목록
     */
    @GetMapping("/list")
    public Page<RoomRetrieveResponseDto> retrievePublicRooms(@HasMember SessionMemberDto sessionMemberDto,
                                                             @RequestParam(value = "page", defaultValue = "1") int page) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        if (page > 0) {
            page -= 1;
        }
        Page<Room> publicRooms = roomService.getRoomsByVisibleAsPaging(page);
        return RoomRetrieveResponseDto.from(publicRooms);
    }

    /**
     * 내가 방장인 채팅방 목록
     * GET /api/v1/room/own/list
     *
     * @param sessionMemberDto 인증
     * @param page 페이지
     * @return 내가 방장인 채팅방 페이징 목록
     */
    @GetMapping("/own/list")
    public Page<RoomRetrieveResponseDto> retrieveMyRooms(@HasMember SessionMemberDto sessionMemberDto,
                                                         @RequestParam(value = "page", defaultValue = "1") int page) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        if (page > 0) {
            page -= 1;
        }
        Page<Room> roomsByMemberId = roomService.getRoomsByMemberIdAsPaging(sessionMemberDto.getId(), page);
        return RoomRetrieveResponseDto.from(roomsByMemberId);
    }


//    @DeleteMapping("/{roomId}")
//    public RoomStatusResponseDto delete(@PathVariable("roomId") String roomId,
//                                        @HasMember SessionMemberDto sessionMemberDto) {
//        if (ObjectUtils.isEmpty(sessionMemberDto)) {
//            throw new IllegalArgumentException("회원이 아닙니다");
//        }
//        try {
//            roomService.changeRoomLiveStatus(roomId, sessionMemberDto);
//        } catch (IllegalArgumentException e) {
//            throw new RoomException(e.getMessage());
//        }
//        return RoomStatusResponseDto.builder()
//                .key("ok")
//                .message(CHANGE_OK_MESSAGE)
//                .build();
//    }

    /**
     * 채팅방 입장
     * POST /api/v1/room/{roomId}/join
     *
     * @param roomId 채팅방 식별자
     * @param sessionMemberDto 인증
     * @return 응답
     */
    @PostMapping("/{roomId}/join")
    public ResponseEntity<Void> joinRoom(@PathVariable("roomId") String roomId,
                                         @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        try {
            roomService.joinRoom(roomId, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 채팅방 나가기
     * POST /api/v1/room/{roomId}/exit
     *
     * @param roomId 채팅방 식별자
     * @param sessionMemberDto 인증
     * @return 응답
     */
    @PostMapping("/{roomId}/exit")
    public ResponseEntity<Void> exitRoom(@PathVariable("roomId") String roomId,
                                          @HasMember SessionMemberDto sessionMemberDto) {
        if (ObjectUtils.isEmpty(sessionMemberDto)) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        try {
            roomService.exitRoom(roomId, sessionMemberDto);
        } catch (IllegalArgumentException e) {
            throw new RoomException(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    //    @PutMapping("/owner")
//    public RoomStatusResponseDto modifyOwner(@RequestBody RoomStatusRequestDto dto,
//                                        @HasMember SessionMemberDto sessionMemberDto) {
//
//    }

//    @GetMapping("/{roomId}/members")
//    public List<MemberResponseDto> getMembers(@PathVariable("roomId") String roomId,
//                                              @HasMember SessionMemberDto sessionMemberDto) {
//        if (ObjectUtils.isEmpty(sessionMemberDto)) {
//            throw new IllegalArgumentException("회원이 아닙니다");
//        }
//        List<Member> members = roomService.getMembers(roomId, sessionMemberDto);
//        return MemberResponseDto.from(members);
//    }

}
