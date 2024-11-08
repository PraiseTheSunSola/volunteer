package com.volunteer.Control;

import com.volunteer.Service.VolunteerActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VolunteerActivityControl {

    @Autowired
    private VolunteerActivityService service;

    @GetMapping("/fetch-volunteer-data")
    public String fetchVolunteerData() {
        service.fetchAndSaveVolunteerData();
        return "Volunteer data fetched and saved successfully!";
    }
}

