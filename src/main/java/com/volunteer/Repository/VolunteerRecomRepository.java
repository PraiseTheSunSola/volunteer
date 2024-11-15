package com.volunteer.Repository;

import com.volunteer.Entity.VolunteerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VolunteerRecomRepository extends JpaRepository<VolunteerActivity, Long> {
    List<VolunteerActivity> findByProgrmCnContaining(String keywordCn);
    List<VolunteerActivity> findByPostAdresContaining(String keywordAd);
    List<VolunteerActivity> findByActWkdyContaining(String actWkdy);
    List<VolunteerActivity> findByProgrmBgndeBetween(String startDate, String endDate);
    List<VolunteerActivity> findByRcritNmpr(String keywordRn);
    List<VolunteerActivity> findByActBeginTm(String startTime);
    List<VolunteerActivity> findByAdultPosblAtAndYngbgsPosblAt(String y, String n);
    List<VolunteerActivity> findByGrpPosblAt(String grpPosblAt);

    //해야 할 목록 : 신청 페이지, 로그인, 아이디 비밀번호 찾기 페이지의 프론트-백엔드 구현하기
}
