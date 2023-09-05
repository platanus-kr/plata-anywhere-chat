package org.platanus.platachat.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.member.dto.MemberJoinRequest;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.message.service.MessageService;
import org.platanus.platachat.web.room.dto.RoomCreateRequest;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomPublic;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

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
        List<MemberJoinRequest> lists = getMemberJoinRequestDtos();
        for (MemberJoinRequest dto : lists) {
            memberService.join(dto);
        }

        log.info("!!! 채팅방 생성 !!!");

        // 방 만들기
        Member m = memberService.findByEmail("test1@test.com");

        RoomCreateRequest roomCreateRequest = RoomCreateRequest.builder()
                .capacity(100L)
                .description("테스트 채팅방")
                .roomPublic(RoomPublic.AVAILABLE)
                .roomName("테스트방")
                .imageUrl("#")
                .build();

        Room createdRoom = roomService.createRoom(roomCreateRequest, m);
//
//        for (int i = 1; i <= 10; i++) {
//            RoomCreateRequestDto requestDto = RoomCreateRequestDto.builder()
//                    .capacity(100L)
//                    .description("테스트 채팅방" + i + "번째")
//                    .roomPublic(RoomPublic.AVAILABLE)
//                    .roomName("채팅방 " + i + "번째")
//                    .imageUrl("#")
//                    .build();
//            roomService.createRoom(requestDto, m);
//        }
//
//        for (int i = 11; i <= 15; i++) {
//            RoomCreateRequestDto requestDto = RoomCreateRequestDto.builder()
//                    .capacity(100L)
//                    .description("테스트 채팅방" + i + "번째")
//                    .roomPublic(RoomPublic.INVISIBLE)
//                    .roomName("채팅방 " + i + "번째")
//                    .imageUrl("#")
//                    .build();
//            roomService.createRoom(requestDto, m);
//        }
//
//        for (int i = 16; i <= 20; i++) {
//            RoomCreateRequestDto requestDto = RoomCreateRequestDto.builder()
//                    .capacity(100L)
//                    .description("테스트 채팅방" + i + "번째")
//                    .roomPublic(RoomPublic.PRIVATE)
//                    .roomName("채팅방 " + i + "번째")
//                    .imageUrl("#")
//                    .build();
//            roomService.createRoom(requestDto, m);
//        }
//
//        for (int i = 21; i <= 25; i++) {
//            RoomCreateRequestDto requestDto = RoomCreateRequestDto.builder()
//                    .capacity(100L)
//                    .description("테스트 채팅방" + i + "번째")
//                    .roomPublic(RoomPublic.OBSERVER)
//                    .roomName("채팅방 " + i + "번째")
//                    .imageUrl("#")
//                    .build();
//            roomService.createRoom(requestDto, m);
//        }

//        log.info("!!! 메시지 생성 !!!");
//
//        // 메시지 추가
//        messageService.deleteAll();
//        for (int i = 0; i <= 100; i++) {
//            MessagePayload build = MessagePayload.builder()
//                    .message("테스트 " + i + "번째 메시지")
//                    .nickname("닉네임")
//                    .roomId(createdRoom.getId())
//                    .type(MessageType.TEXT)
//                    .timestamp(LocalDateTime.now())
//                    .userId("test1")
//                    .build();
//            messageService.saveMessage(build);
//        }
    }

    private List<MemberJoinRequest> getMemberJoinRequestDtos() {
        List<MemberJoinRequest> lists = List.of(
                MemberJoinRequest.builder()
                        .username("test1")
                        .password("test1")
                        .email("test1@test.com")
                        .nickname("테스트1")
                        .build(),
                MemberJoinRequest.builder()
                        .username("test2")
                        .password("test2")
                        .email("test2@test.com")
                        .nickname("테스트2")
                        .build(),
                MemberJoinRequest.builder()
                        .username("test3")
                        .password("test3")
                        .email("test3@test.com")
                        .nickname("테스트3")
                        .build(),
                MemberJoinRequest.builder()
                        .username("test4")
                        .password("test4")
                        .email("test4@test.com")
                        .nickname("테스트4")
                        .build(),
                MemberJoinRequest.builder()
                        .username("test5")
                        .password("test5")
                        .email("test5@test.com")
                        .nickname("테스트5")
                        .build(),
                MemberJoinRequest.builder()
                        .username("test6")
                        .password("test6")
                        .email("test6@test.com")
                        .nickname("테스트6")
                        .build()
        );
        return lists;
    }

}
