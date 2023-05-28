package org.platanus.platachat.web.message.repository.mongodb;


import org.platanus.platachat.web.message.model.MessagePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageMongoRepository extends MongoRepository<MessagePayload, String> {

    @Query(sort = "{'timestamp':-1}")
    List<MessagePayload> findByRoomId(String roomId);

    @Query(sort = "{'timestamp':-1}")
    Page<MessagePayload> findByRoomId(String roomId, Pageable pageable);

    int countByRoomId(String roomId);
}
