package org.platanus.platachat.message.broker;

@Deprecated
public interface PublishService {
    // 발행하기
    void simpleSendMessage(String message);
}
