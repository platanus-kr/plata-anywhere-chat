package org.platanus.platachat.web.room.dto;

import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record RoomRetrieveResponse(
        String roomId,

        String name,

        String description,

        String imageUrl,

        Long capacity,

        Integer roomMembersCount,

        RoomStatus roomStatus,

        RoomPublic roomPublic,

        List<RoomMemberResponse> participates,

        RoomMemberResponse owner,

        LocalDateTime createdAt
) {

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
        return new RoomRetrieveResponse(
                rm.getId(),
                rm.getName(),
                rm.getDescription(),
                rm.getImageUrl(),
                rm.getCapacity(),
                null,
                rm.getRoomStatus(),
                rm.getRoomPublic(),
                null,
                null,
                rm.getCreated()
        );
    }

    public static RoomRetrieveResponse withEntityGraphFrom(Room rm) {
        List<RoomMemberResponse> participates = new ArrayList<>();
        for (RoomMember participate : rm.getParticipates()) {
            participates.add(RoomMemberResponse.from(participate));
        }
        return new RoomRetrieveResponse(
                rm.getId(),
                rm.getName(),
                rm.getDescription(),
                rm.getImageUrl(),
                rm.getCapacity(),
                rm.getParticipates().size(),
                rm.getRoomStatus(),
                rm.getRoomPublic(),
                participates,
                RoomMemberResponse.from(rm.getOwner()),
                rm.getCreated()
        );
    }
}
