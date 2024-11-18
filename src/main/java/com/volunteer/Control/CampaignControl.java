package com.volunteer.Control;

import com.volunteer.DTO.CampaignDto;
import com.volunteer.Service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class CampaignControl {

    private final CampaignService campaignService;

    // 캠페인 메인 페이지 (카테고리별 캠페인 리스트 필터링)
    @GetMapping("/campaignIndex")
    public String campaignMain(@RequestParam(value = "category", required = false) String category, Model model) {
        List<CampaignDto> campaignDtos = campaignService.getAllCampaigns();
        model.addAttribute("campaignList", campaignDtos);

        return "campaign/campaignIndex";  // 캠페인 메인 페이지를 반환
    }

    // 캠페인 작성 페이지로 이동
    @GetMapping("/campaignWrite")
    public String campaignWrite(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) { // 수정 요청인 경우
            CampaignDto campaign = campaignService.getCampaignById(id);
            model.addAttribute("campaign", campaign);
        }
        return "campaign/campaignWrite";  // 캠페인 작성 페이지 반환
    }

    // 캠페인 글 작성 및 수정 저장 처리
    @PostMapping("/campaignWriteSave")
    public String campaignWriteSave(@RequestParam(value = "id", required = false) Long id, // 수정 시 캠페인 ID
                                    @RequestParam("campaignTitle") String title,  // 캠페인 제목
                                    @RequestParam("campaignItem") String item,  // 캠페인 아이템
                                    @RequestParam("category") String category,  // 캠페인 카테고리
                                    @RequestParam("campaignStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,  // 캠페인 시작일
                                    @RequestParam("campaignEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,  // 캠페인 종료일
                                    RedirectAttributes redirectAttributes) {

        if (title == null || title.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "캠페인 제목은 필수 항목입니다.");
            return "redirect:/campaign/campaignWrite";
        }

        if (startDate.isAfter(endDate)) {
            redirectAttributes.addFlashAttribute("error", "시작일은 종료일보다 이전이어야 합니다.");
            return "redirect:/campaign/campaignWrite";
        }

        try {
            // 수정인지 새 작성인지 확인
            if (id != null) {
                campaignService.updateCampaign(id, title, item, category, startDate, endDate);
                redirectAttributes.addFlashAttribute("message", "캠페인이 성공적으로 수정되었습니다.");
            } else {
                campaignService.writeSave(title, item, category, startDate, endDate);
                redirectAttributes.addFlashAttribute("message", "캠페인이 성공적으로 작성되었습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "캠페인 저장에 실패했습니다. 원인: " + e.getMessage());
        }

        return "redirect:/campaign/campaignIndex";
    }

    // 캠페인 상세 페이지
    @GetMapping("/detail/{id}")
    public String campaignDetail(@PathVariable Long id, Model model) {
        CampaignDto campaign = campaignService.getCampaignById(id);
        if (campaign == null) {
            return "error/404";
        }
        model.addAttribute("campaign", campaign);
        return "campaign/detail";
    }
}
