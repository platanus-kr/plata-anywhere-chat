package org.platanus.platachat.web.room.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;

@Getter
@Builder
@ToString
public class RoomStatusRequestDto {

    private String name;

    @JsonProperty("status")
    private RoomStatus roomStatus;

    @JsonProperty("public")
    private RoomPublic roomPublic;

    private String description;

    private String imageUrl;

    private Long capacity;

    private String ownerMemberId;

}
