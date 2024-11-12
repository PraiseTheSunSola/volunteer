package com.volunteer.Service;

import com.volunteer.Repository.VolunteerRecomRepository;
import com.volunteer.Entity.VolunteerActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerRecomService {
    @Autowired
    private VolunteerRecomRepository volunteerRecomRepository;

    public List<VolunteerActivity> searchByContent(String keywordCn){
        return volunteerRecomRepository.findByProgrmCnContaining(keywordCn);
    }

    public List<VolunteerActivity> searchByAddress(String keywordAd){
        return volunteerRecomRepository.findByPostAdresContaining(keywordAd);
    }

    public List<VolunteerActivity> searchByActWkdy(String actWkdy) {
        return volunteerRecomRepository.findByActWkdyContaining(actWkdy);
    }
}
