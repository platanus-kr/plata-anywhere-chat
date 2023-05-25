package org.platanus.platachat.web.message.repository;

import org.platanus.platachat.web.message.repository.mongodb.MessageMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MessageMongoRepository {
}
