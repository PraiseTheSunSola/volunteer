package com.volunteer.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/intro" )
public class IntroControl {

    // 소개 메인페이지
    @GetMapping("/information")
    public String intro() {return "intro/information";}


    // 사회적약자 소개 상세페이지
    @GetMapping("/citizen")
    public String citizen() {return "intro/citizen";}
}