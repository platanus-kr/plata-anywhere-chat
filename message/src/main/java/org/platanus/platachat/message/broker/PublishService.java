package org.platanus.platachat.message.broker;

public interface PublishService {
    // 발행하기
    void simpleSendMessage(String message);
}
