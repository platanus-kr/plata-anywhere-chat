package org.platanus.platachat.web.room.repository.jpa;

import org.platanus.platachat.web.room.model.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberJpaRepository extends JpaRepository<RoomMember, Long> {
}
