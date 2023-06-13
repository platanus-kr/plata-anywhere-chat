package org.platanus.platachat.web.room.repository.jpa;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RoomMemberJpaRepository extends JpaRepository<RoomMember, String> {

    @Query("select rm.room from RoomMember rm where rm.member.id = :memberId")
    List<Room> findRoomsMemberId(@Param("memberId") String memberId);

    @Query("select rm.room from RoomMember rm where rm.member.id = :memberId")
    Page<Room> findRoomsMemberIdAsPaging(@Param("memberId") String memberId, Pageable pageable);

    Optional<RoomMember> findByRoomAndMember(Room room, Member member);

    Optional<RoomMember> findByRoomIdAndMemberId(String roomId, String memberId);
}
