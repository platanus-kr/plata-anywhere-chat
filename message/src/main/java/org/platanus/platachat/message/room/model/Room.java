package org.platanus.platachat.message.room.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCapacity() {
        return capacity;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public String getOwner() {
        return owner;
    }

    public List<RoomMember> getParticipates() {
        return participates;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
