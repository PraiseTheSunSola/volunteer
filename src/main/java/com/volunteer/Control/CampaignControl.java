package com.volunteer.Control;

import com.volunteer.Service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping( "/campaign" )
public class CampaignControl {

    private final CampaignService campaignService;
    // 캠페인 메인페이지
    @GetMapping("/campaignIndex")
    public String campaignMain(Model mode) {

        return "campaign/campaignIndex";
    }

    // 캠페인 작성
    @GetMapping("/campaignWrite")
    public String campaignWrite(Model mode) {
        return "campaign/campaignWrite";
    }
    @PostMapping("/campaignWriteSave")
    public String campaignWriteSave(@RequestParam("campaignTitle") String save, @RequestParam("campaignItem") String campaignItem)
    {
        campaignService.writeSave(save, campaignItem);
        return "redirect:/campaign/campaignIndex";}
}
