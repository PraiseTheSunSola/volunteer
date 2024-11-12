package com.volunteer.Control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping( "/campaign" )
public class CampaignControl {

    // 캠페인 메인페이지
    @GetMapping("/campaignIndex")
    public String campaignMain(Model mode) {return "campaign/campaignIndex";}

    // 캠페인 작성
    @GetMapping("/campaignWrite")
    public String campaignWrite(Model mode) {return "campaign/campaignWrite";}
}
