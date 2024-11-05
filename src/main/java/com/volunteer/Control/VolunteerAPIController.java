package com.volunteer.Control;

import com.volunteer.Service.VolunteerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VolunteerAPIController {
    private final VolunteerService volunteerService;

    public VolunteerAPIController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/fetch-data")
    public String fetchData() {
        volunteerService.fetchAndSaveData();
        return "Data fetched and saved!";
    }
}
