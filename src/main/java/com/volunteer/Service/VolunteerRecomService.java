package com.volunteer.Service;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Repository.VolunteerRecomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerRecomService {

    @Autowired
    private VolunteerRecomRepository volunteerRecomRepository;


    public List<VolunteerActivity> findAll() {
        return volunteerRecomRepository.findAll();
    }

    public List<VolunteerActivity> search(String keywordCn, String keywordAd, String keywordRn, String weekday,
                                          String startDate, String endDate, String startTime, String ageOption, String group) {
        List<VolunteerActivity> activities = volunteerRecomRepository.findAll();

        if (keywordCn != null && !keywordCn.isEmpty()) {
            activities = filterByContent(activities, keywordCn);
        }
        if (keywordAd != null && !keywordAd.isEmpty()) {
            activities = filterByAddress(activities, keywordAd);
        }
        if (keywordRn != null && !keywordRn.isEmpty()) {
            activities = filterByRecurit(activities, keywordRn);
        }
        if (weekday != null && !weekday.isEmpty()) {
            activities = filterByWeekday(activities, weekday);
        }
        if (startDate != null && endDate != null) {
            activities = filterByDateRange(activities, startDate, endDate);
        }
        if (startTime != null && !startTime.isEmpty()) {
            activities = filterByStartTime(activities, startTime);
        }
        if (ageOption != null && !ageOption.isEmpty()) {
            activities = filterByAgeOption(activities, ageOption);
        }
        if (group != null && !group.isEmpty()) {
            activities = filterByGroup(activities, group);
        }
        return activities;
    }

    // 각 필터링 메서드
    private List<VolunteerActivity> filterByContent(List<VolunteerActivity> activities, String keywordCn) {
        return volunteerRecomRepository.findByProgrmCnContaining(keywordCn);
    }

    private List<VolunteerActivity> filterByAddress(List<VolunteerActivity> activities, String keywordAd) {
        return volunteerRecomRepository.findByPostAdresContaining(keywordAd);
    }

    private List<VolunteerActivity> filterByRecurit(List<VolunteerActivity> activities, String keywordRn) {
        return volunteerRecomRepository.findByRcritNmpr(keywordRn);
    }

    private List<VolunteerActivity> filterByWeekday(List<VolunteerActivity> activities, String weekday) {
        return volunteerRecomRepository.findByActWkdyContaining(weekday);
    }

    private List<VolunteerActivity> filterByDateRange(List<VolunteerActivity> activities, String startDate, String endDate) {
        return volunteerRecomRepository.findByProgrmBgndeBetween(startDate, endDate);
    }

    private List<VolunteerActivity> filterByStartTime(List<VolunteerActivity> activities, String startTime) {
        return volunteerRecomRepository.findByActBeginTm(startTime);
    }

    private List<VolunteerActivity> filterByAgeOption(List<VolunteerActivity> activities, String ageOption) {
        if ("adultOnly".equals(ageOption)) {
            return volunteerRecomRepository.findByAdultPosblAtAndYngbgsPosblAt("Y", "N");
        } else if ("youthOnly".equals(ageOption)) {
            return volunteerRecomRepository.findByAdultPosblAtAndYngbgsPosblAt("N", "Y");
        } else if ("both".equals(ageOption)) {
            return volunteerRecomRepository.findByAdultPosblAtAndYngbgsPosblAt("Y", "Y");
        }
        return activities;
    }

    private List<VolunteerActivity> filterByGroup(List<VolunteerActivity> activities, String group) {
        if ("Y".equals(group)) {
            return volunteerRecomRepository.findByGrpPosblAt("Y");
        } else if ("N".equals(group)) {
            return volunteerRecomRepository.findByGrpPosblAt("N");
        }
        return activities;
    }

    public Optional<VolunteerActivity> findById(Long id) {
        return volunteerRecomRepository.findById(id);
    }
}
