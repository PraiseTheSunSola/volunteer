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

    public List<VolunteerActivity> searchByDateRange(String formattedStartDate, String formattedEndDate) {
        return volunteerRecomRepository.findByProgrmBgndeBetween(formattedStartDate, formattedEndDate);
    }

    public List<VolunteerActivity> searchByRecurit(String keywordRn) {
        return volunteerRecomRepository.findByRcritNmpr(keywordRn);
    }

    public List<VolunteerActivity> searchByActBeginTm(String startTime) {
        return volunteerRecomRepository.findByActBeginTm(startTime);
    }

    public List<VolunteerActivity> searchByAgeOption(String ageOption) {
        if ("adultOnly".equals(ageOption)) {
            return volunteerRecomRepository.findByAdultPosblAtAndYngbgsPosblAt("Y", "N");
        } else if ("youthOnly".equals(ageOption)) {
            return volunteerRecomRepository.findByAdultPosblAtAndYngbgsPosblAt("N", "Y");
        } else if ("both".equals(ageOption)) {
            return volunteerRecomRepository.findByAdultPosblAtAndYngbgsPosblAt("Y", "Y");
        }

        return volunteerRecomRepository.findAll(); // 조건이 없으면 전체 반환
    }

    public List<VolunteerActivity> searchByGrope(String grope) {
        if ("Y".equals(grope)) {
            // 단체 가능인 경우
            return volunteerRecomRepository.findByGrpPosblAt("Y");
        } else if ("N".equals(grope)) {
            // 단체 불가능인 경우
            return volunteerRecomRepository.findByGrpPosblAt("N");
        }
        // 예외적인 경우나 모든 데이터를 반환하고 싶다면 기본값을 설정할 수 있습니다.
        return volunteerRecomRepository.findAll();
    }

    public List<VolunteerActivity> findAll() {
        return volunteerRecomRepository.findAll();
    }
}
