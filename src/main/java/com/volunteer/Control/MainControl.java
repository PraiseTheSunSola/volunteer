package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Service.VolunteerRecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainControl {

    @GetMapping("/")
    public String MainPage(Model model){
        return "index";
    }

}
