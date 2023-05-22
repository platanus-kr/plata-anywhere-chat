package org.platanus.platachat.web.room.repository;

import java.util.List;

import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.repository.jpa.RoomJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends RoomJpaRepository {

}