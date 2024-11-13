package com.volunteer.Control;

import com.volunteer.DTO.CampaignDto;
import com.volunteer.Service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class CampaignControl {

    private final CampaignService campaignService;

    // 캠페인 메인 페이지 (카테고리별 캠페인 리스트 필터링)
    @GetMapping("/campaignIndex")
    public String campaignMain(@RequestParam(value = "category", required = false) String category, Model model) {
        List<CampaignDto> campaignList;

        // 카테고리별 캠페인 필터링
        if (category != null && !category.isEmpty()) {
            // 특정 카테고리의 캠페인만 가져오기
            campaignList = campaignService.getCampaignsByCategory(category);
        } else {
            // 모든 캠페인 가져오기
            campaignList = campaignService.getAllCampaigns();
        }

        // 각 카테고리별로 필터링된 캠페인 리스트 생성
        List<CampaignDto> signCampaigns = campaignList.stream()
                .filter(campaign -> "sign".equals(campaign.getCategory()))
                .collect(Collectors.toList());

        List<CampaignDto> joinCampaigns = campaignList.stream()
                .filter(campaign -> "join".equals(campaign.getCategory()))
                .collect(Collectors.toList());

        List<CampaignDto> lastCampaigns = campaignList.stream()
                .filter(campaign -> "last".equals(campaign.getCategory()))
                .collect(Collectors.toList());

        // 필터링된 캠페인 리스트를 모델에 추가
        model.addAttribute("campaignList", campaignList);
        model.addAttribute("signCampaigns", signCampaigns);
        model.addAttribute("joinCampaigns", joinCampaigns);
        model.addAttribute("lastCampaigns", lastCampaigns);

        return "campaign/campaignIndex";
    }

    // 캠페인 작성 페이지
    @GetMapping("/campaignWrite")
    public String campaignWrite() {
        return "campaign/campaignWrite";
    }

    // 캠페인 글 작성 저장 처리 (카테고리 포함)
    @PostMapping("/campaignWriteSave")
    public String campaignWriteSave(@RequestParam("campaignTitle") String title,
                                    @RequestParam("campaignItem") String item,
                                    @RequestParam("category") String category) {
        // 캠페인 정보 저장
        campaignService.writeSave(title, item, category);
        return "redirect:/campaign/campaignIndex";  // 캠페인 리스트 페이지로 리다이렉트
    }
}
