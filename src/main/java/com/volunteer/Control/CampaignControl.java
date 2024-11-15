package com.volunteer.Control;

import com.volunteer.DTO.CampaignDto;
import com.volunteer.Service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        List<CampaignDto>  campaignDtos = campaignService.getAllCampaigns();
        model.addAttribute("campaignList",campaignDtos);



        return "campaign/campaignIndex";  // 캠페인 메인 페이지를 반환
    }

    // 카테고리별 캠페인 필터링 (종료된 캠페인은 제외, 'last' 카테고리는 예외)
    private List<CampaignDto> filterCampaigns(List<CampaignDto> campaigns, String category) {
        return campaigns.stream()
                .filter(campaign -> category.equals(campaign.getCategory()) &&
                        !isPastCampaign(campaign))  // 종료된 캠페인 제외
                .collect(Collectors.toList());  // 필터링된 캠페인 리스트 반환
    }

    // 캠페인 종료 여부 확인
    private boolean isPastCampaign(CampaignDto campaign) {
        Instant currentInstant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();  // 현재 시간 가져오기
        Instant campaignEndInstant = campaign.getEndDate().atZone(ZoneId.systemDefault()).toInstant(); // campaign의 종료일을 Instant로 변환
        return campaignEndInstant.isBefore(currentInstant);  // 종료일이 현재보다 이전이면 종료된 캠페인
    }

    // 캠페인 작성 페이지로 이동
    @GetMapping("/campaignWrite")
    public String campaignWrite() {
        return "campaign/campaignWrite";  // 캠페인 작성 페이지를 반환
    }

    // 캠페인 글 작성 저장 처리 (카테고리 포함)
    @PostMapping("/campaignWriteSave")
    public String campaignWriteSave(@RequestParam("campaignTitle") String title,  // 캠페인 제목
                                    @RequestParam("campaignItem") String item,  // 캠페인 아이템
                                    @RequestParam("category") String category,  // 캠페인 카테고리
                                    @RequestParam("campaignStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,  // 캠페인 시작일
                                    @RequestParam("campaignEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,  // 캠페인 종료일
                                    RedirectAttributes redirectAttributes) {  // 리다이렉트 시 전달할 속성

        // 캠페인 제목이 비어있을 경우 오류 메시지 추가 후 캠페인 작성 페이지로 리다이렉트
        if (title == null || title.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "캠페인 제목은 필수 항목입니다.");
            return "redirect:/campaign/campaignWrite";  // 캠페인 작성 페이지로 리다이렉트
        }

        // 시작일이 종료일보다 늦을 경우 오류 메시지 추가 후 캠페인 작성 페이지로 리다이렉트
        if (startDate.isAfter(endDate)) {
            redirectAttributes.addFlashAttribute("error", "시작일은 종료일보다 이전이어야 합니다.");
            return "redirect:/campaign/campaignWrite";  // 캠페인 작성 페이지로 리다이렉트
        }

        try {
            // 캠페인 저장 처리
            campaignService.writeSave(title, item, category, startDate, endDate);
            redirectAttributes.addFlashAttribute("message", "캠페인이 성공적으로 작성되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시 오류 메시지 추가
            redirectAttributes.addFlashAttribute("error", "캠페인 작성에 실패했습니다. 원인: " + e.getMessage());
        }

        return "redirect:/campaign/campaignIndex";  // 캠페인 메인 페이지로 리다이렉트
    }

    // 캠페인 상세 페이지
    @GetMapping("/detail/{id}")
    public String campaignDetail(@PathVariable Long id, Model model) {
        // 주어진 ID로 캠페인 정보 가져오기
        CampaignDto campaign = campaignService.getCampaignById(id);

        // 캠페인이 존재하지 않으면 404 페이지로 리다이렉트
        if (campaign == null) {
            return "error/404";  // 404 에러 페이지 반환
        }

        // 캠페인 정보를 모델에 추가
        model.addAttribute("campaign", campaign);
        return "campaign/detail";  // 캠페인 상세 페이지 반환
    }
}
