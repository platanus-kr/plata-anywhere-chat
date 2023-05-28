package org.platanus.platachat.web.room.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomWebController {

    @GetMapping("/list")
    public String listRoom() {
        return "room_list.html";
    }

    @GetMapping("/create")
    public String createRoom() {
        return "room_create";
    }

}
