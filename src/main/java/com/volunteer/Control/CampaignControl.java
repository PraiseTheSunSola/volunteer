package com.volunteer.Control;

import com.volunteer.Entity.CampaignEntity;
import com.volunteer.Service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class CampaignControl {

    private final CampaignService campaignService;

    // 캠페인 메인페이지
    @GetMapping("/campaignIndex")
    public String campaignMain(Model model) {
        // 캠페인 리스트를 가져오는 서비스 호출
        List<CampaignEntity> campaignList = campaignService.getAllCampaigns();
        model.addAttribute("campaignList", campaignList);
        return "campaign/campaignIndex";
    }

    // 캠페인 작성 페이지
    @GetMapping("/campaignWrite")
    public String campaignWrite() {
        return "campaign/campaignWrite";
    }

    // 캠페인 글 작성 저장 처리
    @PostMapping("/campaignWriteSave")
    public String campaignWriteSave(@RequestParam("campaignTitle") String title,
                                    @RequestParam("campaignItem") String item) {

        // 캠페인 정보를 서비스 계층에 전달하여 저장
        campaignService.writeSave(title, item);

        return "redirect:/campaign/campaignIndex";  // 캠페인 리스트 페이지로 리다이렉트
    }
}
