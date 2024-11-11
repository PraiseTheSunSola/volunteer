package com.volunteer.Control;


import com.volunteer.Service.VolunteerActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerCSVControl {
    @Autowired
    private VolunteerActivityService volunteerActivityService;

    @RequestMapping("/upload-csv")
    public String uploadCsvToDb() {
        try {
            volunteerActivityService.saveCsvToDatabase();
            return "CSV 저장완료";
        } catch (IOException e) {
            e.printStackTrace();
            return "CSV 저장 오류 : " + e.getMessage();
        }
    }
}


