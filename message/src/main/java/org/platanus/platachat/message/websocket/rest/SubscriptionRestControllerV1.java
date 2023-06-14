package org.platanus.platachat.message.websocket.rest;

import lombok.RequiredArgsConstructor;
import org.platanus.platachat.message.websocket.subscription.SubscriptionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionRestControllerV1 {

    private final SubscriptionManager subscriptionManager;

    /**
     * <h3>채팅방 인원 집계</h3>
     * GET /api/v1/subscription/{roomId}/count
     *
     * @param roomId 채팅방 식별자
     * @return 채팅방 참여 인원
     */
    @GetMapping("/{roomId}/count")
    public Mono<Long> getCountRoomMemberInRoom(@PathVariable String roomId) {
        return Mono.just(subscriptionManager.countRoomMemberInRoom(roomId));
    }
}
