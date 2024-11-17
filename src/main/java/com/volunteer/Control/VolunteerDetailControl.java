package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Service.VolunteerRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/volunteer")

public class VolunteerDetailControl {

    @Autowired
    private VolunteerRecomService volunteerRecomService;


    @GetMapping("/detail/{id}")
    public String getVolunteerDetail(@PathVariable("id") Long id, Model model) {
        Optional<VolunteerActivity> optionalActivity = volunteerRecomService.findById(id);
        if (optionalActivity.isPresent()) {
            model.addAttribute("activity", optionalActivity.get());
            return "volunteer/volunteer_detail"; // 정상적인 상세 페이지로 이동
        } else {
            // 에러 페이지로 이동하거나 에러 메시지 반환
            model.addAttribute("errorMessage", "해당 봉사활동을 찾을 수 없습니다. ID: " + id);
            return "error"; // error.html 템플릿 필요
        }
    }
}
