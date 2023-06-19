package org.platanus.platachat.message.message.repository.mongo;

import org.platanus.platachat.message.message.model.MessagePayload;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface MessageCrudRepository extends ReactiveCrudRepository<MessagePayload, String> {

}
