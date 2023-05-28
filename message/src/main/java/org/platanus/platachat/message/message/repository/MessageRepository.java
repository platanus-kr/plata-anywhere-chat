package org.platanus.platachat.message.message.repository;

import org.platanus.platachat.message.message.repository.mongo.MessageCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MessageCrudRepository {
}
