package org.platanus.platachat.web.chat.repository;

import org.platanus.platachat.web.chat.repository.mongo.MessageMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MessageMongoRepository {
}
