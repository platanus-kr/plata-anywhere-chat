package org.platanus.platachat.web.room.model;

import lombok.*;
import org.platanus.platachat.web.member.model.BaseTime;
import org.platanus.platachat.web.member.model.Member;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@ToString(exclude = {"participates"}, callSuper = true)
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
}

