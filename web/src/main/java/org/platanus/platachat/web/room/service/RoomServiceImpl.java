package org.platanus.platachat.web.room.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.constants.RoomConstant;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomsRetrieveResponseDto;
import org.platanus.platachat.web.room.dto.RoomStatusRequestDto;
import org.platanus.platachat.web.room.model.*;
import org.platanus.platachat.web.room.repository.RoomMemberRepository;
import org.platanus.platachat.web.room.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomMemberRepository roomMemberRepository;
    private final RoomRepository roomRepository;
    private final MemberService memberService;


    @Override
    public Room createRoom(RoomCreateRequestDto roomReqDto, Member m) {
        // 채팅방 생성
        Room r = Room.builder()
                .name(roomReqDto.getRoomName())
                .description(roomReqDto.getDescription())
                .imageUrl(roomReqDto.getImageUrl())
                .capacity(roomReqDto.getCapacity())
                .roomPublic(roomReqDto.getRoomPublic())
                .roomStatus(RoomStatus.ALIVE)
                .owner(m)
                .build();
        Room toRoom = createRoom(r);

        // 채팅방 초기 맴버 생성 (채팅방 오너)
        RoomMember rm = RoomMember.builder()
                .member(m)
                .role(RoomRole.SYSOP)
                .status(RoomMemberStatus.ALIVE)
                .room(toRoom)
                .build();
        addRoomMember(rm);
        toRoom.addRoomMember(rm);

        return toRoom;
    }


    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public RoomMember addRoomMember(RoomMember roomMember) {;
        Optional<RoomMember> rm = roomMemberRepository.findByRoomAndMember(roomMember.getRoom(), roomMember.getMember());
        // upsert를 지원하는게 아니라서 entity 검사를 해야함.
        if (rm.isPresent()) {
            RoomMember rmPersistence = rm.get();
            rmPersistence.setJoinDateTime(LocalDateTime.now());
            return roomMemberRepository.save(rmPersistence);
        }
        return roomMemberRepository.save(roomMember);
    }

    @Override
    public Room getRoomById(String id, SessionMemberDto sessionMemberDto) {
        // owner, participates
        Room room = roomRepository.findWithRoomById(id)
                .orElseThrow(() -> new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MESSAGE));
        if (room.getRoomPublic() == RoomPublic.PRIVATE) {
            throw new IllegalArgumentException(RoomConstant.ROOM_PRIVATE_ROOM_MESSAGE);
        }
        // 비공개 방인 경우 방에 참여한 사용자만 가능
        if (room.getRoomPublic() == RoomPublic.INVISIBLE) {
            room.getParticipates().stream()
                    .filter(rm -> StringUtils.equals(rm.getMember().getId(), sessionMemberDto.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MESSAGE));
//            if (!StringUtils.equals(room.getOwner().getId(), sessionMemberDto.getId())) {
//                throw new IllegalArgumentException(ROOM_NOT_FOUND_ROOM_MESSAGE//            }
        }
        if (room.getRoomStatus() == RoomStatus.ENDED) {
            throw new IllegalArgumentException(RoomConstant.ROOM_ALREADY_ENDED_ROOM_MESSAGE);
        }
        return room;
    }

    @Override
    public Room changeRoomInformation(String roomId,
                                      RoomStatusRequestDto requestDto,
                                      SessionMemberDto sessionMemberDto) {
        validateDto(roomId, requestDto);

        Member requestMember = memberService.findById(sessionMemberDto.getId());
        Member roomOwner = roomRepository.findWithOwnerById(roomId).getOwner();

        // 채팅방 소유자만 변경 가능
        if (!StringUtils.equals(requestMember.getId(), roomOwner.getId())) {
            throw new IllegalArgumentException(RoomConstant.ROOM_NOT_OWNER_VALIDATE_FAILED_MESSAGE);
        }

        Room build = Room.builder()
                .id(roomId)
                .name(requestDto.getName())
                .roomStatus(requestDto.getRoomStatus())
                .roomPublic(requestDto.getRoomPublic())
                .description(requestDto.getDescription())
                .imageUrl(requestDto.getImageUrl())
                .capacity(requestDto.getCapacity())
                .owner(requestMember)
                .build();
        return roomRepository.save(build);
    }

    @Override
    public Room changeRoomOwner(String roomId,
                                RoomStatusRequestDto requestDto,
                                SessionMemberDto sessionMemberDto) {
        validateDto(roomId, requestDto);

        Member requestMember = memberService.findById(sessionMemberDto.getId());
        Member roomOwner = roomRepository.findWithOwnerById(roomId).getOwner();
        String ownerMemberId = requestDto.getOwnerMemberId();

        // 채팅방 소유자만 변경 가능
        if (!StringUtils.equals(requestMember.getId(), roomOwner.getId())) {
            throw new IllegalArgumentException(RoomConstant.ROOM_NOT_OWNER_VALIDATE_FAILED_MESSAGE);
        }

        // 현재 방장을 방장으로 교체할 수 없음.
        if (StringUtils.equals(roomOwner.getId(), ownerMemberId)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_ALREADY_ROOM_OWNER_MESSAGE);
        }

        Member changedOwner = memberService.findById(ownerMemberId);

        Room build = Room.builder()
                .id(roomId)
                .name(requestDto.getName())
                .roomStatus(requestDto.getRoomStatus())
                .roomPublic(requestDto.getRoomPublic())
                .description(requestDto.getDescription())
                .imageUrl(requestDto.getImageUrl())
                .capacity(requestDto.getCapacity())
                .owner(changedOwner)
                .build();
        return roomRepository.save(build);
    }

    @Override
    public Page<Room> getRoomsByVisibleAsPaging(int page) {
//        final int PAGE_SIZE = 10;
        final int PAGE_SIZE = 100;
        return getRoomsByVisibleAsPaging(page, PAGE_SIZE);
    }

    @Override
    public Page<Room> getRoomsByVisibleAsPaging(int page, int size) {
        return roomRepository.findAllByRoomStatusAndRoomPublicNot(RoomStatus.ALIVE, RoomPublic.INVISIBLE, PageRequest.of(page, size));
    }

    @Override
    public List<Room> getRoomsByMemberId(String memberId) {
        /**
         * select * from ROOMS as R
         * inner join ROOM_MEMBER as RM on RM.ROOM_ID = R.ID
         * where RM.MEMBER_ID = {id}
         */
        List<Room> roomsMemberId = roomMemberRepository.findRoomsMemberId(memberId);
        return roomsMemberId;
    }

    @Override
    public Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page) {
        final int PAGE_SIZE = 10;
        return getRoomsByMemberIdAsPaging(memberId, page, PAGE_SIZE);
    }

    @Override
    public Page<Room> getRoomsByMemberIdAsPaging(String memberId, int page, int size) {
        return roomMemberRepository.findRoomsMemberIdAsPaging(memberId, PageRequest.of(page, size));
    }

    @Override
    public Page<RoomsRetrieveResponseDto> getRoomDtosByMemberIdAsPaging(String memberId, int page) {
        final int PAGE_SIZE = 10;
        return getRoomDtosByMemberIdAsPaging(memberId, page, PAGE_SIZE);
    }

    @Override
    public Page<RoomsRetrieveResponseDto> getRoomDtosByMemberIdAsPaging(String memberId, int page, int size) {
        return roomMemberRepository.findRoomsByMemberIdWithPagination(memberId, PageRequest.of(page, size));
    }

    @Override
    public void exitRoom(String roomId, SessionMemberDto sessionMemberDto) {
        RoomMember roomMember = roomMemberRepository.findByRoomIdAndMemberId(roomId, sessionMemberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MEMBER_MESSAGE));
        if (roomMember.getRole() == RoomRole.SYSOP) {
            throw new IllegalArgumentException(RoomConstant.ROOM_NOT_EXIT_OWNER_MESSAGE);
        }
        try {
            roomMember.setRoomMemberStatus(RoomMemberStatus.EXITED);
//            roomMemberRepository.delete(roomMember);
        } catch (Exception e) {
            throw new IllegalArgumentException(RoomConstant.ROOM_EXIT_FAILED_MESSAGE);
        }
    }

    @Override
    public void joinRoom(String roomId, SessionMemberDto sessionMemberDto) {
        Optional<RoomMember> opt = roomMemberRepository.findByRoomIdAndMemberId(roomId, sessionMemberDto.getId());
        if (opt.isPresent()) {
            throw new IllegalArgumentException(RoomConstant.ROOM_ALREADY_JOIN_ROOM_MESSAGE);
        }
        Member m = memberService.findById(sessionMemberDto.getId());

        Room toRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MESSAGE));

        // 여기에 RoomPublic 상황에 따른 분기 필요.


        RoomMemberStatus roomMemberStatus = RoomMemberStatus.ALIVE;
        RoomRole roomRole = RoomRole.MEMBER;

        // 옵저버 방인 경우 회원 상태 적용
        if (toRoom.getRoomPublic() == RoomPublic.OBSERVER) {
            roomMemberStatus = RoomMemberStatus.VOID;
            roomRole = RoomRole.OBSERVER;
        }

        RoomMember rm = RoomMember.builder()
                .member(m)
                .role(roomRole)
                .status(roomMemberStatus)
                .room(toRoom)
                .build();
        addRoomMember(rm);
    }

    @Override
    public Room validateChatSessionAsPublic(String roomId, SessionMemberDto sessionMemberDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MESSAGE));
        if (room.getRoomPublic().equals(RoomPublic.INVISIBLE)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MESSAGE);
        }
        if (room.getRoomPublic().equals(RoomPublic.PRIVATE)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_PRIVATE_ROOM_MESSAGE);
        }
        if (room.getRoomStatus() == RoomStatus.ENDED) {
            throw new IllegalArgumentException(RoomConstant.ROOM_ALREADY_ENDED_ROOM_MESSAGE);
        }
        if (room.getCapacity() <= room.getParticipates().size()) {
            throw new IllegalArgumentException(RoomConstant.ROOM_PARTICIPATES_OVER_MESSAGE);
        }
        return room;
    }

    @Override
    public boolean validateRoomMemberInChat(String roomId, String memberId) {
        RoomMember roomMember = roomMemberRepository.findByRoomIdAndMemberId(roomId, memberId)
                .orElseThrow(() -> new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MEMBER_MESSAGE));
        return true;
    }

    @Override
    public void endChat(String roomId, SessionMemberDto sessionMemberDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(RoomConstant.ROOM_NOT_FOUND_ROOM_MESSAGE));
        if (!room.getOwner().getId().equals(sessionMemberDto.getId())) {
            throw new IllegalArgumentException(RoomConstant.ROOM_END_CHAT_VALIDATE_OWNER_MESSAGE);
        }
        room.setRoomStatus(RoomStatus.ENDED);

        // 여기 채팅 종료 시그널 보내서 종료시키는 webclient 넣어야됨.

        roomRepository.save(room);
    }

    private void validateDto(String roomId, RoomStatusRequestDto dto) {
        if (StringUtils.isBlank(roomId)) {
            throw new IllegalArgumentException(RoomConstant.ROOM_VALIDATE_ROOM_ID_IS_BLANK_MESSAGE);
        }
        if (StringUtils.isBlank(dto.getName())) {
            throw new IllegalArgumentException(RoomConstant.ROOM_VALIDATE_ROOM_NAME_IS_BLANK_MESSAGE);
        }
        if (StringUtils.isBlank(dto.getOwnerMemberId())) {
            throw new IllegalArgumentException(RoomConstant.ROOM_VALIDATE_OWNER_IS_BLANK_MESSAGE);
        }
        if (dto.getRoomStatus() == null) {
            throw new IllegalArgumentException(RoomConstant.ROOM_VALIDATE_STATUS_IS_BLANK_MESSAGE);
        }
        if (dto.getRoomPublic() == null) {
            throw new IllegalArgumentException(RoomConstant.ROOM_VALIDATE_PUBLIC_IS_BLANK_MESSAGE);
        }
        if (dto.getCapacity() == null || dto.getCapacity() < 1L) {
            throw new IllegalArgumentException(RoomConstant.ROOM_VALIDATE_CAPACITY_IS_BLANK_MESSAGE);
        }
//        if (dto.getCapacity() > 100L) {
//            throw new IllegalArgumentException("채팅방 최대 인원은 100명을 넘을 수 없습니다.");
//        }
    }

}
