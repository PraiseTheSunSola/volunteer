package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Service.VolunteerRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VolunteerRecomControl {
    @Autowired
    private VolunteerRecomService volunteerRecomService;

    @GetMapping("/volunteer/search")
    public String searchVolunteerActivities(@RequestParam("keyword") String keyword, Model model){
        List<VolunteerActivity> volunteerActivities = volunteerRecomService.searchByTitle(keyword);
        model.addAttribute("volunteerActivity", volunteerActivities);
        return "volunteer";
    }
}
