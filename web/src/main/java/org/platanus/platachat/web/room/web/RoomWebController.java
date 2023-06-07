package org.platanus.platachat.web.room.web;


import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.platanus.platachat.web.auth.argumentresolver.HasMember;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.service.MemberService;
import org.platanus.platachat.web.room.model.Room;
import org.platanus.platachat.web.room.model.RoomMember;
import org.platanus.platachat.web.room.model.RoomMemberStatus;
import org.platanus.platachat.web.room.model.RoomRole;
import org.platanus.platachat.web.room.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomWebController {
    
    @GetMapping("/create")
    public String createRoom(Model model,
                             @HasMember SessionMemberDto sessionMemberDto,
                             HttpServletRequest request) {
        return null;
    }
    
    @GetMapping("/modify/{roomId}")
    public String modifyRoom(Model model,
                             @PathVariable(required = true) String roomId,
                             @HasMember SessionMemberDto sessionMemberDto,
                             HttpServletRequest request) {
        return null;
    }
    
    

}
