package org.platanus.platachat.message.broker;

import org.platanus.platachat.message.broker.dto.BrokerMessageDto;

public interface ConsumerComponent {
    void consume(BrokerMessageDto request);
}
