package com.volunteer.Control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/intro" )
public class IntroControl {

    // 소개 메인페이지
    @GetMapping("information")
    public String intro() {return "intro/information";}

    // 미션 소개페이지
    @GetMapping("/mission")
    public String mission() {return "intro/mission";}

}