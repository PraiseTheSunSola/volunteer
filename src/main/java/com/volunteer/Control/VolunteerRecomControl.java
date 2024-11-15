package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Service.VolunteerRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

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
            @RequestParam(value = "keywordRn", required = false) String keywordRn,
            @RequestParam(value = "weekday", required = false) String weekday,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "ageOption", required = false) String ageOption,
            @RequestParam(value = "group", required = false) String group,
            Model model) {

        List<VolunteerActivity> volunteerActivities = volunteerRecomService.search(
                keywordCn, keywordAd, keywordRn, weekday, startDate, endDate, startTime, ageOption, group);

        model.addAttribute("volunteer_activity", volunteerActivities != null ? volunteerActivities : Collections.emptyList());
        return "volunteer/volunteer";
    }
}
