package org.platanus.platachat.web.room.repository.jpa;


import com.google.common.collect.ImmutableList;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.model.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface RoomJpaRepository extends JpaRepository<Room, String> {


    // with는 메서드 이름을 더 이해하기 쉽게 만들기 위한 도구일 뿐, JPA 쿼리 메서드의 연산자나 키워드가 아님.
    @EntityGraph(attributePaths = {"owner", "participates"})
    Room findWithOwnerById(String id);

    @EntityGraph(attributePaths = {"owner", "participates"})
    Optional<Room> findWithRoomById(String id);

    Page<Room> findAllByRoomPublic(RoomPublic status, Pageable pageable);

//    @EntityGraph(attributePaths = {"owner", "participates"})
    Page<Room> findAllByRoomStatusAndRoomPublicNot(RoomStatus roomStatus, RoomPublic roomPublic, Pageable pageable);
}
