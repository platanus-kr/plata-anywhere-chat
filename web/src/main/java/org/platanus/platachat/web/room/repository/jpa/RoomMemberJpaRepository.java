package org.platanus.platachat.web.room.repository.jpa;

import java.util.List;

import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface RoomMemberJpaRepository extends JpaRepository<RoomMember, String> {
	
	@Query("select rm.room from RoomMember rm where rm.member.id = :memberId")
	List<Room> findRoomsMemberId(@Param("memberId") String memberId);
}
