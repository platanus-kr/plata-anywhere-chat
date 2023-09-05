package org.platanus.platachat.web.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class RoomRetrieveResponse {
    private String roomId;

    private String name;

    private String description;

    private String imageUrl;

    private Long capacity;

    private Integer roomMembersCount;

    private RoomStatus roomStatus;

    private RoomPublic roomPublic;

    private List<RoomMemberResponse> participates;

    private RoomMemberResponse owner;

    private LocalDateTime createdAt;

    public static List<RoomRetrieveResponse> from(List<Room> list) {
        List<RoomRetrieveResponse> dtos = new ArrayList<>();
        for (Room room : list) {
            dtos.add(RoomRetrieveResponse.from(room));
        }
        return dtos;
    }

    public static Page<RoomRetrieveResponse> from(Page<Room> list) {
        List<RoomRetrieveResponse> dtos = new ArrayList<>();
        for (Room room : list) {
            dtos.add(RoomRetrieveResponse.from(room));
        }
        return new PageImpl<>(dtos, list.getPageable(), list.getTotalElements());
    }

    public static RoomRetrieveResponse from(Room rm) {
        return RoomRetrieveResponse.builder()
                .roomId(rm.getId())
                .name(rm.getName())
                .description(rm.getDescription())
                .imageUrl(rm.getImageUrl())
                .capacity(rm.getCapacity())
//                .roomMembersCount(rm.getParticipates().size())
                .roomStatus(rm.getRoomStatus())
                .roomPublic(rm.getRoomPublic())
//                .participates(null)
//                .owner(RoomMemberResponseDto.from(rm.getOwner())) // select 발생 지점
                .createdAt(rm.getCreated())
                .build();
    }

    public static RoomRetrieveResponse withEntityGraphFrom(Room rm) {
        List<RoomMemberResponse> participates = new ArrayList<>();
        for (RoomMember participate : rm.getParticipates()) {
            participates.add(RoomMemberResponse.from(participate));
        }
        return RoomRetrieveResponse.builder()
                .roomId(rm.getId())
                .name(rm.getName())
                .description(rm.getDescription())
                .imageUrl(rm.getImageUrl())
                .capacity(rm.getCapacity())
                .roomMembersCount(rm.getParticipates().size())
                .roomStatus(rm.getRoomStatus())
                .roomPublic(rm.getRoomPublic())
                .participates(participates)
                .owner(RoomMemberResponse.from(rm.getOwner()))
                .createdAt(rm.getCreated())
                .build();
    }
}
