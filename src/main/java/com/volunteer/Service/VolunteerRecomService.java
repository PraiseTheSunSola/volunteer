package com.volunteer.Service;

import com.volunteer.Entity.VolunteerActivity;
import com.volunteer.Repository.VolunteerRecomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerRecomService {

    @Autowired
    private VolunteerRecomRepository volunteerRecomRepository;

    public List<VolunteerActivity> findAll() {
        return volunteerRecomRepository.findAll();
    }

    public List<VolunteerActivity> search(String keywordCn, String keywordAd, String keywordRn, List<Integer> weekday,
                                          LocalDate startDate, LocalDate endDate, List<Integer> startTime, String ageOption, String group) {
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
            activities = volunteerRecomRepository.findByActBeginTm(startTime.get(0)); // 첫 번째 값만 전달
        }

        if (ageOption != null && !ageOption.isEmpty()) {
            activities = filterByAgeOption(activities, ageOption);
        }
        if (group != null && !group.isEmpty()) {
            activities = filterByGroup(activities, group);
        }
        return activities;
    }

    private List<VolunteerActivity> filterByContent(List<VolunteerActivity> activities, String keywordCn) {
        return volunteerRecomRepository.findByProgrmCnContaining(keywordCn);
    }

    private List<VolunteerActivity> filterByAddress(List<VolunteerActivity> activities, String keywordAd) {
        return volunteerRecomRepository.findByPostAdresContaining(keywordAd);
    }

    private List<VolunteerActivity> filterByRecurit(List<VolunteerActivity> activities, String keywordRn) {
        return volunteerRecomRepository.findByRcritNmpr(keywordRn);
    }

    private List<VolunteerActivity> filterByWeekday(List<VolunteerActivity> activities, List<Integer> weekday) {
        // 빈 리스트 또는 null 처리
        if (weekday == null || weekday.isEmpty()) {
            // 요일 조건이 없는 경우 전체 활동 반환
            return volunteerRecomRepository.findAll();
        }
        Integer weekday1 = weekday.size() > 0 ? weekday.get(0) : null;
        Integer weekday2 = weekday.size() > 1 ? weekday.get(1) : null;
        Integer weekday3 = weekday.size() > 2 ? weekday.get(2) : null;
        Integer weekday4 = weekday.size() > 3 ? weekday.get(3) : null;
        Integer weekday5 = weekday.size() > 4 ? weekday.get(4) : null;
        Integer weekday6 = weekday.size() > 5 ? weekday.get(5) : null;
        Integer weekday7 = weekday.size() > 6 ? weekday.get(6) : null;

        System.out.printf("Mapped params: %d, %d, %d, %d, %d, %d, %d%n",
                weekday1, weekday2, weekday3, weekday4, weekday5, weekday6, weekday7);

        return volunteerRecomRepository.findByWeekdays(
                weekday1, weekday2, weekday3, weekday4, weekday5, weekday6, weekday7
        );
    }

    private List<VolunteerActivity> filterByDateRange(List<VolunteerActivity> activities, LocalDate startDate, LocalDate endDate) {
        return volunteerRecomRepository.findByProgrmBgndeBetween(startDate, endDate);
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
