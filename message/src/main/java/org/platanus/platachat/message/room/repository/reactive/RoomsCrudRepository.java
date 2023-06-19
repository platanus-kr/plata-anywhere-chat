package org.platanus.platachat.message.room.repository.reactive;

import org.platanus.platachat.message.room.model.Room;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

public interface RoomsCrudRepository extends ReactiveCrudRepository<Room, String> {
}
