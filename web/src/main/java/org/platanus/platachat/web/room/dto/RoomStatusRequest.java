package org.platanus.platachat.web.room.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;

public record RoomStatusRequest(

    String name,

    @JsonProperty("status") RoomStatus roomStatus,

    @JsonProperty("public") RoomPublic roomPublic,

    String description,

    String imageUrl,

    Long capacity,

    String ownerMemberId
) {
}
