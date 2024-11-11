package com.volunteer.Control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/donation")
public class DonationControl {

    // 기부 메인페이지
    @GetMapping("/informationIndex")
    public String donationMain() {return "donation/informationIndex";}
}
