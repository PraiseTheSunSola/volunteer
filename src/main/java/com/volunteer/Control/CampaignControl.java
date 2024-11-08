package com.volunteer.Control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping( "/campaign" )
public class CampaignControl {

    // 캠페인 메인페이지
    @GetMapping("/campaignIndex")
    public String campaignMain() {return "campaign/campaignIndex";}
}
