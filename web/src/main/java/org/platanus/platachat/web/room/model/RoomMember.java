package org.platanus.platachat.web.room.model;

import lombok.*;
import org.platanus.platachat.web.member.model.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROOMS_MEMBER", indexes = {
        @Index(name = "idx_room_id", columnList = "ROOM_ID")
})
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
}
