package org.platanus.platachat.web.room.repository;

import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.repository.jpa.RoomMemberJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomMemberRepository extends RoomMemberJpaRepository {
    List<Room> findRoomsMemberId(@Param("memberId") String memberId);
    Page<Room> findRoomsMemberIdAsPaging(@Param("memberId") String memberId, Pageable pageable);
}
