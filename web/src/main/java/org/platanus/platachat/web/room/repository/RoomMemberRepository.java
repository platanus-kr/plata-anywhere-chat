package org.platanus.platachat.web.room.repository;

import java.util.List;

import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.repository.jpa.RoomMemberJpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomMemberRepository extends RoomMemberJpaRepository {
	List<Room> findRoomsMemberId(@Param("memberId") String memberId);
}
