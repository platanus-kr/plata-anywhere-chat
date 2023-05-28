package org.platanus.platachat.web.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomStatusResponseDto {
    private String key;
    private String message;

}
