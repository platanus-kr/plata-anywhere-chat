package org.platanus.platachat.message.room.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Rooms")
@TypeAlias("Room")
public class Room {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long capacity;
    private RoomStatus roomStatus;
    private String owner;
    private List<RoomMember> participates;
    private LocalDateTime created;
}
