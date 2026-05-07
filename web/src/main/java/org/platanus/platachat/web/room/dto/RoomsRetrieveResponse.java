package org.platanus.platachat.web.room.dto;

import java.time.LocalDateTime;

public record RoomsRetrieveResponse(
        String roomId,

        String name,

        String description,

        String imageUrl,

        LocalDateTime createdAt
) {
}
