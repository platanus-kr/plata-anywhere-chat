package org.platanus.platachat.web.room.repository.jpa;


import java.util.List;

import org.platanus.platachat.web.room.model.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomJpaRepository extends JpaRepository<Room, Long> {
}
