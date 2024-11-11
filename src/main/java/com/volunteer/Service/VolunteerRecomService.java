package com.volunteer.Service;

import com.volunteer.Control.VolunteerRecomRepository;
import com.volunteer.Entity.VolunteerActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerRecomService {
    @Autowired
    private VolunteerRecomRepository volunteerRecomRepository;

    public List<VolunteerActivity> searchByTitle(String keyword){
        return volunteerRecomRepository.findByProgrmSjContaining(keyword);
    }
}
