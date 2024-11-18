package com.volunteer.Control;

import com.volunteer.DTO.ParticipationDto;
import com.volunteer.Service.ParticipationService;
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
@RequestMapping("/participation")
public class ParticipationControl {

    private final ParticipationService participationService;

    // 참여 메인 페이지
    @GetMapping("/participationIndex")
    public String participationMain(@RequestParam(value = "category", required = false) String category, Model model) {
        List<ParticipationDto> participationDtos = participationService.getAllParticipations();
        model.addAttribute("participationList", participationDtos);
        return "participation/participationIndex";  // 참여 리스트 페이지
    }

    // 참여 글 작성 페이지로 이동 (수정시 기존 데이터를 가져와서 수정 폼에 채움)
    @GetMapping("/participationWrite")
    public String participationWrite(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            // 수정 모드: 기존 참여 글을 가져오기
            ParticipationDto participation = participationService.getParticipationById(id);
            if (participation == null) {
                return "error/404";  // 참여 글이 없으면 404로 처리
            }
            model.addAttribute("participation", participation);
        } else {
            // 새 글 작성: 빈 DTO 객체를 전달
            model.addAttribute("participation", null);  // 수정된 부분: null로 설정
        }
        return "participation/participationWrite";  // 참여 글 작성 페이지
    }


    // 참여 글 작성 저장 처리 (수정 기능 포함)
    @PostMapping("/participationWriteSave")
    public String participationWriteSave(@RequestParam(value = "id", required = false) Long id,
                                         @RequestParam("participationTitle") String title,
                                         @RequestParam("participationItem") String item,
                                         @RequestParam("category") String category,
                                         @RequestParam("participationStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                         @RequestParam("participationEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                         RedirectAttributes redirectAttributes) {

        // 제목이 비었을 경우 오류 메시지
        if (title == null || title.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "참여 제목은 필수 항목입니다.");
            return "redirect:/participation/participationWrite";  // 경로 소문자로 수정
        }

        // 시작일이 종료일보다 늦을 경우 오류 메시지
        if (startDate.isAfter(endDate)) {
            redirectAttributes.addFlashAttribute("error", "시작일은 종료일보다 이전이어야 합니다.");
            return "redirect:/participation/participationWrite";  // 경로 소문자로 수정
        }

        try {
            if (id == null) {
                // 신규 참여 글 작성
                participationService.writeSave(title, item, category, startDate, endDate);
                redirectAttributes.addFlashAttribute("message", "참여가 성공적으로 작성되었습니다.");
            } else {
                // 참여 글 수정
                participationService.updateParticipation(id, title, item, category, startDate, endDate);
                redirectAttributes.addFlashAttribute("message", "참여가 성공적으로 수정되었습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "참여 처리에 실패했습니다. 원인: " + e.getMessage());
        }

        // 수정 후 상세 페이지로 리다이렉트하거나 목록으로 리다이렉트할 수 있음
        if (id != null) {
            return "redirect:/participation/detail/" + id;  // 수정 후 해당 상세 페이지로 이동
        } else {
            return "redirect:/participation/participationIndex";  // 새 글 작성 후 참여 목록 페이지로 리다이렉트
        }
    }

    // 참여 상세 페이지
    @GetMapping("/detail/{id}")
    public String participationDetail(@PathVariable Long id, Model model) {
        ParticipationDto participation = participationService.getParticipationById(id);

        if (participation == null) {
            return "error/404";
        }

        model.addAttribute("participation", participation);
        return "participation/detail";  // 참여 상세 페이지
    }
}
