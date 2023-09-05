package org.platanus.platachat.web.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomStatusResponse {
    private String key;
    private String message;

}
