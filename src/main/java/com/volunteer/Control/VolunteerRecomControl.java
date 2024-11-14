package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Service.ActivityUtilityService;
import com.volunteer.Service.VolunteerRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/volunteer")
public class VolunteerRecomControl {
    @Autowired
    private VolunteerRecomService volunteerRecomService;

    @Autowired
    private ActivityUtilityService activityUtilityService;

    @GetMapping
    public String loadAllVolunteerActivities(Model model) {
        List<VolunteerActivity> allActivities = volunteerRecomService.findAll();
        model.addAttribute("volunteer_activity",  allActivities != null ? allActivities : Collections.emptyList());
        return "volunteer/volunteer";
    }

    @GetMapping("/searchRn") // 인원 검색
    public String searchRnVolunteerAcitivires(@RequestParam("keywordRn") String keywordRn, Model model){
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByRecurit(keywordRn);
        model.addAttribute("volunteer_activity", volunteerActivities);
        return "volunteer/volunteer";
    }

    @GetMapping("/searchCn") // 내용 검색
    public String searchCnVolunteerActivities(@RequestParam("keywordCn") String keywordCn, Model model) {
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByContent(keywordCn);
        model.addAttribute("volunteer_activity", volunteerActivities);
        return "volunteer/volunteer";
    }

    @GetMapping("/searchAd") // 주소 검색
    public String searchAdVolunteerActivities(@RequestParam("keywordAd") String keywordAd, Model model) {
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByAddress(keywordAd);
        model.addAttribute("volunteer_activity", volunteerActivities);
        return "volunteer/volunteer";
    }

    @GetMapping("/searchWk") // 요일 검색
    public String searchWkVolunteerActivities(@RequestParam("weekday") String weekday, Model model) {
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByActWkdy("1"); // 1이 포함된 모든 요일을 가져옴

        // actWkdy 보정 및 요일 필터링
        List<VolunteerActivity> filteredActivities = volunteerActivities.stream()
                .map(activity -> {
                    String normalizedActWkdy = String.format("%07d", activity.getActWkdy()); // actWkdy를 7자리 문자열로 변환
                    activity.setActWkdy(Integer.parseInt(normalizedActWkdy)); // 필요한 경우, 변환 후 저장
                    return activity;
                })
                .filter(activity -> {
                    String actWkdyStr = String.format("%07d", activity.getActWkdy());
                    return actWkdyStr.charAt(getDayIndex(weekday)) == '1'; // 지정 요일에 활동 가능한지 확인
                })
                .collect(Collectors.toList());

        model.addAttribute("volunteer_activity", filteredActivities);
        return "volunteer/volunteer";
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

    @GetMapping("/searchDate")  //날짜 검색
    public String searchDateRangeVolunteerActivities(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model) {

        // 시작일자와 종료일자가 YYYYMMDD 형식인지 확인
        if (startDate.length() == 8 && endDate.length() == 8) {
            String formattedStartDate = startDate.substring(2, 4) + "," + startDate.substring(4, 6) + "," + startDate.substring(6, 8);
            String formattedEndDate = endDate.substring(2, 4) + "," + endDate.substring(4, 6) + "," + endDate.substring(6, 8);

            // Service에서 범위 검색 수행
            List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByDateRange(formattedStartDate, formattedEndDate);
            model.addAttribute("volunteer_activity", volunteerActivities);
            return "volunteer/volunteer";
        } else {
            model.addAttribute("error", "봉사일자를 YYYYMMDD 형식으로 입력해 주세요.");
            return "volunteer/volunteer"; // 오류 메시지를 표시할 페이지
        }
    }

    @GetMapping("/searchTm")
    public String searchTimeVolunteerActivities(@RequestParam("startTime") String startTime, Model model){
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByActBeginTm(startTime);
        model.addAttribute("volunteer_activity", volunteerActivities);
        return "volunteer/volunteer";
    }

    @GetMapping("/searchAge")
    public String searchAgeOptionVolunteerActivities(@RequestParam("ageOption") String ageOption, Model model){
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByAgeOption(ageOption);
        model.addAttribute("volunteer_activity", volunteerActivities);
        return "volunteer/volunteer";
    }

    @GetMapping("/searchGrope")
    public String searchGroupVolunteerActivities(@RequestParam("grope") String grope, Model model){
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByGrope(grope);
        model.addAttribute("volunteer_activity", volunteerActivities);
        return "volunteer/volunteer";
    }

}
