package org.platanus.platachat.message.broker;

import org.platanus.platachat.message.chat.dto.BrokerRequestDto;

public interface ListenerService {
    // 리스너
    String simpleListener(String message);

    String dtoListener(BrokerRequestDto dto);
}
