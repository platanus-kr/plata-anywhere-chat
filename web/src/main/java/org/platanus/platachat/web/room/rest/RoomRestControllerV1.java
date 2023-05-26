package org.platanus.platachat.web.room.rest;

import java.util.List;

import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomCreateResponseDto;
import org.platanus.platachat.web.room.dto.RoomMemberResponseDto;
import org.platanus.platachat.web.room.dto.RoomRetrieveResponseDto;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomRestControllerV1 {
	
	private final MemberService memberService;
	private final RoomService roomService;
	
	@PostMapping
	public RoomCreateResponseDto create(@RequestBody(required = true) RoomCreateRequestDto dto,
										@HasMember SessionMemberDto sessionMemberDto) {
		if (ObjectUtils.isEmpty(sessionMemberDto)) {
			throw new IllegalArgumentException("회원이 아닙니다");
		}
		
		RoomCreateResponseDto build = createChatRoom(dto, sessionMemberDto);
		
		return build;
	}
	
	private RoomCreateResponseDto createChatRoom(RoomCreateRequestDto roomReqDto, SessionMemberDto smDto) {
		
		// 세션에서 회원 추출
		Member m = memberService.findById(smDto.getId());
		
		// 채팅방 생성 후 최초 참여자 추가
		Room r = roomService.createRoom(roomReqDto, m);
		
		RoomCreateResponseDto build = RoomCreateResponseDto.builder()
				.roomId(r.getId())
				.participates(List.of(RoomMemberResponseDto.from(m, r.getParticipates().stream().findAny().orElseThrow())))
				.build();
		return build;
	}
	

	
	@GetMapping("/list")
	public List<RoomRetrieveResponseDto> getMyRooms(@HasMember SessionMemberDto sessionMemberDto,
													@RequestParam(value = "page", defaultValue = "1") int page) {
		if (page > 0) {
			page -= 1;
		}
		List<Room> roomsByMemberId = roomService.getRoomsByMemberId(sessionMemberDto.getId());
		return RoomRetrieveResponseDto.from(roomsByMemberId);
	}
	
}
