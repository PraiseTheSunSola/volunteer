package com.volunteer.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageControl {
    //마이페이지 메인
    @GetMapping("")
    public String mypage() {
        return "mypage/mypageIndex";
    }

    //내 정보
    @GetMapping("/info")
    public String info() {
        return "mypage/myinfo";
    }

    //봉사활동 신청 내역
    @GetMapping("/volunteer")
    public String volunteer() {
        return "mypage/myVolunteer";
    }

    //기부 내역
    @GetMapping("/donation")
    public String donation() {
        return "mypage/myDonation";
    }
}