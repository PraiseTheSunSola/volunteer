package com.volunteer.Control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping( "/intro" )
public class IntroControl {

    // 소개 메인페이지
    @GetMapping("/information")
    public String intro() {
        return "intro/information";  // 반환 경로: /src/main/resources/templates/intro/information.html
    }

    // 미션 소개페이지
    @GetMapping("/mission")
    public String mission() {
        return "intro/mission";  // 반환 경로: /src/main/resources/templates/intro/mission.html
    }

}