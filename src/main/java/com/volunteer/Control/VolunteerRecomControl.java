package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Service.VolunteerRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/volunteer")
public class VolunteerRecomControl {

    @Autowired
    private VolunteerRecomService volunteerRecomService;

    // 기본 경로 매핑
    @GetMapping
    public String loadAllVolunteerActivities(Model model) {
        List<VolunteerActivity> allActivities = volunteerRecomService.findAll();
        model.addAttribute("volunteer_activity", allActivities != null ? allActivities : Collections.emptyList());
        return "volunteer/volunteer"; // volunteer/volunteer.html 템플릿 반환
    }

    //검색 요청 매핑
    @GetMapping("/search")
    public String searchVolunteerActivities(
            @RequestParam(value = "keywordCn", required = false) String keywordCn,
            @RequestParam(value = "keywordAd", required = false) String keywordAd,
            @RequestParam(value = "keywordRn", required = false) Integer keywordRn,
            @RequestParam(value = "weekday", required = false) List<Integer> weekday,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "startTime", required = false) List<Integer> startTime,
            @RequestParam(value = "ageOption", required = false) String ageOption,
            @RequestParam(value = "group", required = false) String group,
            Model model) {

        // 빈 값 처리
        if (weekday == null || weekday.isEmpty()) {
            weekday = Collections.emptyList();; // 명시적으로 null로 설정
        }


        List<VolunteerActivity> volunteerActivities = volunteerRecomService.search(
                keywordCn, keywordAd, keywordRn, weekday, startDate, endDate, startTime, ageOption, group);

        model.addAttribute("volunteer_activity", volunteerActivities != null ? volunteerActivities : Collections.emptyList());
        return "volunteer/volunteerSearch";
    }

    @GetMapping("/detail/{id}")
    public String getVolunteerDetail(@PathVariable("id") Long id, Model model) {
        Optional<VolunteerActivity> optionalActivity = volunteerRecomService.findById(id);
        if (optionalActivity.isPresent()) {
            model.addAttribute("activity", optionalActivity.get());
            return "volunteer/volunteerDetail"; // 정상적인 상세 페이지로 이동
        } else {
            // 에러 페이지로 이동하거나 에러 메시지 반환
            model.addAttribute("errorMessage", "해당 봉사활동을 찾을 수 없습니다. ID: " + id);
            return "error"; // error.html 템플릿 필요
        }
    }
}
