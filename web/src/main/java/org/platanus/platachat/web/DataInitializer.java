package org.platanus.platachat.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.member.dto.MemberJoinRequest;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.message.model.MessagePayload;
import org.platanus.platachat.web.message.model.MessageType;
import org.platanus.platachat.web.message.service.MessageService;
import org.platanus.platachat.web.room.dto.RoomCreateRequest;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Profile("!production")
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberService memberService;
    private final RoomService roomService;
    private final MessageService messageService;

    @PostConstruct
    public void init() {

        log.info("!!! 회원 생성 !!!");

        // 회원 추가
        List<MemberJoinRequest> lists = getMemberJoinRequests();
        for (MemberJoinRequest dto : lists) {
            memberService.join(dto);
        }

        log.info("!!! 채팅방 생성 !!!");

        // 방 만들기
        Member m = memberService.findByEmail("test1@test.com");

        RoomCreateRequest roomCreateRequest = new RoomCreateRequest(
                "테스트방",
                "테스트 채팅방",
                "#",
                100L,
                RoomPublic.AVAILABLE
        );

        Room createdRoom = roomService.createRoom(roomCreateRequest, m);

        for (int i = 1; i <= 5; i++) {
            RoomCreateRequest requestDto = new RoomCreateRequest(
                    "채팅방 " + i + "번째",
                    "테스트 채팅방" + i + "번째",
                    "#",
                    100L,
                    RoomPublic.AVAILABLE
            );
            roomService.createRoom(requestDto, m);
        }

        for (int i = 6; i <= 10; i++) {
            RoomCreateRequest requestDto = new RoomCreateRequest(
                    "채팅방 " + i + "번째",
                    "테스트 채팅방" + i + "번째",
                    "#",
                    100L,
                    RoomPublic.INVISIBLE
            );
            roomService.createRoom(requestDto, m);
        }

        for (int i = 11; i <= 15; i++) {
            RoomCreateRequest requestDto = new RoomCreateRequest(
                    "채팅방 " + i + "번째",
                    "테스트 채팅방" + i + "번째",
                    "#",
                    100L,
                    RoomPublic.PRIVATE
            );
            roomService.createRoom(requestDto, m);
        }

        for (int i = 16; i <= 20; i++) {
            RoomCreateRequest requestDto = new RoomCreateRequest(
                    "채팅방 " + i + "번째",
                    "테스트 채팅방" + i + "번째",
                    "#",
                    100L,
                    RoomPublic.OBSERVER
            );
            roomService.createRoom(requestDto, m);
        }

        log.info("!!! 메시지 생성 !!!");

        // 메시지 추가
        messageService.deleteAll();
        for (int i = 0; i <= 20; i++) {
            MessagePayload build = new MessagePayload(
                    null,
                    createdRoom.getId(),
                    "test1",
                    "닉네임",
                    "테스트 " + i + "번째 메시지",
                    LocalDateTime.now(),
                    MessageType.TEXT
            );
            messageService.saveMessage(build);
        }
    }

    private List<MemberJoinRequest> getMemberJoinRequests() {
        List<MemberJoinRequest> lists = List.of(
                new MemberJoinRequest("test1", "test1", "테스트1", "test1@test.com"),
                new MemberJoinRequest("test2", "test2", "테스트2", "test2@test.com"),
                new MemberJoinRequest("test3", "test3", "테스트3", "test3@test.com"),
                new MemberJoinRequest("test4", "test4", "테스트4", "test4@test.com"),
                new MemberJoinRequest("test5", "test5", "테스트5", "test5@test.com"),
                new MemberJoinRequest("test6", "test6", "테스트6", "test6@test.com")
        );
        return lists;
    }

}
