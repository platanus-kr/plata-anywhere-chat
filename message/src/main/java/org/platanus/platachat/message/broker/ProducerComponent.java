package org.platanus.platachat.message.broker;

import org.platanus.platachat.message.broker.dto.BrokerSendRequestDto;

public interface ProducerComponent {
    void sendMessage(BrokerSendRequestDto request);
}
