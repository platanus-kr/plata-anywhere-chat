package org.platanus.platachat.web.room.rest;

import java.util.List;

import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomCreateResponseDto;
import org.platanus.platachat.web.room.dto.RoomMemberDto;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomMemberStatus;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomRole;
import org.platanus.platachat.web.room.model.RoomStatus;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		
		// 채팅방 생성
		Room r = Room.builder()
				.name(dto.getRoomName())
				.description(dto.getDescription())
				.imageUrl(dto.getImageUrl())
				.capacity(dto.getCapacity())
				.roomPublic(dto.getRoomPublic())
				.roomStatus(RoomStatus.ALIVE)
				.build();
		Room saveRoom = roomService.createRoom(r);
		
		// 채팅방 초기 맴버 생성 (채팅방 오너)
		Member m = memberService.findById(sessionMemberDto.getId());
		RoomMember rm = RoomMember.builder()
				.member(m)
				.role(RoomRole.SYSOP)
				.status(RoomMemberStatus.ALIVE)
				.room(saveRoom)
				.build();
		RoomMember saveRoomMember = roomService.addRoomMember(rm);
		
		// 응답 생성
		RoomCreateResponseDto build = RoomCreateResponseDto.builder()
				.roomId(saveRoom.getId())
				.participates(List.of(RoomMemberDto.from(m, saveRoomMember)))
				.build();
		
		return build;
	}
	
}
