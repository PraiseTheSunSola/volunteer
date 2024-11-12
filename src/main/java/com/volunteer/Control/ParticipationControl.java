package com.volunteer.Control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping( "/participation" )
public class ParticipationControl {

    // 회원 참여 공간  메인페이지
    @GetMapping("/participationIndex")
    public String participationMain(Model mode) {return "participation/participationIndex";}
}
