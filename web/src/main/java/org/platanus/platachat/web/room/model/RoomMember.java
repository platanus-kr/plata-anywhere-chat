package org.platanus.platachat.web.room.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.platanus.platachat.web.member.model.Member;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString(exclude = {"member", "room"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROOMS_MEMBER",
        indexes = {
                @Index(name = "idx_room_id", columnList = "ROOM_ID")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"MEMBER_ID", "ROOM_ID"})
        }
)
@Entity
public class RoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private LocalDateTime joinDateTime;

    private LocalDateTime exitDateTime;

    @Enumerated(value = EnumType.STRING)
    private RoomRole role;

    @Enumerated(value = EnumType.STRING)
    private RoomMemberStatus status;

    private LocalDateTime voidEndDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public void setJoinDateTime(LocalDateTime joinDateTime) {
        this.joinDateTime = joinDateTime;
    }

    public void setRoomMemberStatus(RoomMemberStatus rms) {
        status = rms;
    }
}
