package org.platanus.platachat.web.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class RoomsRetrieveResponse {
    private String roomId;

    private String name;

    private String description;

    private String imageUrl;

    private LocalDateTime createdAt;
}
