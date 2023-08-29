package org.platanus.platachat.web.room.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.platanus.platachat.web.member.model.BaseTime;
import org.platanus.platachat.web.member.model.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
//@ToString(exclude = {"participates"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROOMS", indexes = {
        @Index(name = "idx_room_public", columnList = "roomPublic, roomStatus")
})
@Entity
public class Room extends BaseTime {

    @Id
    @Column(length = 36)
    private String id;

    private String name;

    private String description;

    private String imageUrl;

    private Long capacity;

    @Enumerated(value = EnumType.STRING)
    private RoomStatus roomStatus;

    @Enumerated(value = EnumType.STRING)
    private RoomPublic roomPublic;

    @OneToMany(mappedBy = "room")
    private List<RoomMember> participates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_MEMBER_ID")
    private Member owner;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public void addRoomMember(RoomMember rm) {
        if (this.participates == null) {
            this.participates = new ArrayList<>();
        }
        participates.add(rm);
    }
}

