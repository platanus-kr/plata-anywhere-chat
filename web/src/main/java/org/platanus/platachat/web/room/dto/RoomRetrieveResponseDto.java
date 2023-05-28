package org.platanus.platachat.web.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class RoomRetrieveResponseDto {
    private String roomId;

    private String name;

    private String description;

    private String imageUrl;

    private Long capacity;

    private Integer roomMembersCount;

    private RoomStatus roomStatus;

    private RoomPublic roomPublic;

    private List<RoomMemberResponseDto> participates;

    private RoomMemberResponseDto owner;

    public static List<RoomRetrieveResponseDto> from(List<Room> list) {
        List<RoomRetrieveResponseDto> dtos = new ArrayList<>();
        for (Room room : list) {
            dtos.add(RoomRetrieveResponseDto.from(room));
        }
        return dtos;
    }

    public static Page<RoomRetrieveResponseDto> from(Page<Room> list) {
        List<RoomRetrieveResponseDto> dtos = new ArrayList<>();
        for (Room room : list) {
            dtos.add(RoomRetrieveResponseDto.from(room));
        }
        return new PageImpl<>(dtos, list.getPageable(), list.getTotalElements());
    }

    public static RoomRetrieveResponseDto from(Room rm) {
        return RoomRetrieveResponseDto.builder()
                .roomId(rm.getId())
                .name(rm.getName())
                .description(rm.getDescription())
                .imageUrl(rm.getImageUrl())
                .capacity(rm.getCapacity())
                .roomMembersCount(rm.getParticipates().size())
                .roomStatus(rm.getRoomStatus())
                .roomPublic(rm.getRoomPublic())
                .participates(null)
                .owner(RoomMemberResponseDto.from(rm.getOwner())) // select 발생 지점
                .build();
    }
}
