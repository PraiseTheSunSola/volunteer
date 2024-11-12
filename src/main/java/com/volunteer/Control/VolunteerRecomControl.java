package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Service.ActivityUtilityService;
import com.volunteer.Service.VolunteerRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VolunteerRecomControl {
    @Autowired
    private VolunteerRecomService volunteerRecomService;

    @Autowired
    private ActivityUtilityService activityUtilityService;

    @GetMapping("/volunteer/searchCn") // 내용 검색
    public String searchCnVolunteerActivities(@RequestParam("keywordCn") String keywordCn, Model model) {
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByContent(keywordCn);
        model.addAttribute("volunteerActivity", volunteerActivities);
        return "volunteer";
    }

    @GetMapping("/volunteer/searchAd") // 주소 검색
    public String searchAdVolunteerActivities(@RequestParam("keywordAd") String keywordAd, Model model) {
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByAddress(keywordAd);
        model.addAttribute("volunteerActivity", volunteerActivities);
        return "volunteer";
    }

    @GetMapping("/volunteer/searchWk") // 요일 검색
    public String searchWkVolunteerActivities(@RequestParam("weekday") String weekday, Model model) {
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByActWkdy("1"); // 1이 포함된 모든 요일을 가져옴

        // actWkdy 보정 및 요일 필터링
        List<VolunteerActivity> filteredActivities = volunteerActivities.stream()
                .map(activity -> {
                    activity.setActWkdy(activityUtilityService.normalizeActWkdy(activity.getActWkdy()));
                    return activity;
                })
                .filter(activity -> activity.getActWkdy().charAt(getDayIndex(weekday)) == '1') // 지정 요일에 활동 가능한지 확인
                .collect(Collectors.toList());

        model.addAttribute("volunteerActivity", filteredActivities);
        return "volunteer";
    }

    // 요일 인덱스를 반환하는 유틸리티 메서드
    private int getDayIndex(String weekday) {
        switch (weekday.toLowerCase()) {
            case "mon": return 0;
            case "tue": return 1;
            case "wed": return 2;
            case "thu": return 3;
            case "fri": return 4;
            case "sat": return 5;
            case "sun": return 6;
            default: throw new IllegalArgumentException("Invalid weekday: " + weekday);
        }
    }




}
