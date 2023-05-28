package org.platanus.platachat.web.room.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.dto.RoomCreateRequestDto;
import org.platanus.platachat.web.room.dto.RoomStatusRequestDto;
import org.platanus.platachat.web.room.model.*;
import org.platanus.platachat.web.room.repository.RoomMemberRepository;
import org.platanus.platachat.web.room.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return toRoom;
    }


    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public RoomMember addRoomMember(RoomMember roomMember) {
        return roomMemberRepository.save(roomMember);
    }

    @Override
    public Room getRoomById(String id, SessionMemberDto sessionMemberDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));
        if (room.getRoomPublic() == RoomPublic.PRIVATE) {
            throw new IllegalArgumentException("비공개 방 입니다.");
        }
        if (room.getRoomPublic() == RoomPublic.INVISIBLE) {
            if (!StringUtils.equals(room.getOwner().getId(), sessionMemberDto.getId())) {
                throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
            }
        }
        if (room.getRoomStatus() == RoomStatus.ENDED) {
            throw new IllegalArgumentException("종료된 채팅방입니다.");
        }
        return room;
    }

    @Override
    public Room changeRoomInformation(RoomStatusRequestDto requestDto,
                                      SessionMemberDto sessionMemberDto) {
        validateDto(requestDto);

        Member requestMember = memberService.findById(sessionMemberDto.getId());
        Member roomOwner = roomRepository.findWithOwnerById(requestDto.getRoomId()).getOwner();

        if (!StringUtils.equals(requestMember.getId(), roomOwner.getId())) {
            throw new IllegalArgumentException("채팅방의 소유자만 변경할 수 있습니다.");
        }

        Room build = Room.builder()
                .id(requestDto.getRoomId())
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
    public Room changeRoomOwner(RoomStatusRequestDto requestDto) {
        return null;
    }

    @Override
    public Page<Room> getRoomsByVisibleAsPaging(int page) {
        final int PAGE_SIZE = 10;
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

    private void validateDto(RoomStatusRequestDto dto) {
        if (StringUtils.isBlank(dto.getRoomId())) {
            throw new IllegalArgumentException("채팅방 아이디는 필수입니다.");
        }
        if (StringUtils.isBlank(dto.getName())) {
            throw new IllegalArgumentException("채팅방 이름은 필수입니다.");
        }
        if (StringUtils.isBlank(dto.getOwnerMemberId())) {
            throw new IllegalArgumentException("채팅방 주인은 필수입니다.");
        }
        if (dto.getRoomStatus() == null) {
            throw new IllegalArgumentException("채팅방 상태는 필수입니다.");
        }
        if (dto.getRoomPublic() == null) {
            throw new IllegalArgumentException("채팅방 공개여부는 필수입니다.");
        }
        if (dto.getCapacity() == null || dto.getCapacity() < 1L) {
            throw new IllegalArgumentException("채팅방 최대 인원은 필수입니다.");
        }
//        if (dto.getCapacity() > 100L) {
//            throw new IllegalArgumentException("채팅방 최대 인원은 100명을 넘을 수 없습니다.");
//        }
    }

}
