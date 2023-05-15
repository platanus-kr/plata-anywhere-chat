package org.platanus.platachat.message.chat.repository;

import org.platanus.platachat.message.chat.model.MessagePayload;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesCrudRepository extends ReactiveCrudRepository<MessagePayload, String> {

}
