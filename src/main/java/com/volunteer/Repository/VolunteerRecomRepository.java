package com.volunteer.Repository;

import com.volunteer.Entity.VolunteerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface VolunteerRecomRepository extends JpaRepository<VolunteerActivity, Long> {

    List<VolunteerActivity> findByProgrmCnContaining(String keywordCn);

    List<VolunteerActivity> findByPostAdresContaining(String keywordAd);

    @Query("SELECT v FROM VolunteerActivity v WHERE "
            + "(:weekday1 IS NULL OR LENGTH(v.actWkdy) >= :weekday1 AND SUBSTRING(v.actWkdy, :weekday1, 1) = '1') AND "
            + "(:weekday2 IS NULL OR LENGTH(v.actWkdy) >= :weekday2 AND SUBSTRING(v.actWkdy, :weekday2, 1) = '1') AND "
            + "(:weekday3 IS NULL OR LENGTH(v.actWkdy) >= :weekday3 AND SUBSTRING(v.actWkdy, :weekday3, 1) = '1') AND "
            + "(:weekday4 IS NULL OR LENGTH(v.actWkdy) >= :weekday4 AND SUBSTRING(v.actWkdy, :weekday4, 1) = '1') AND "
            + "(:weekday5 IS NULL OR LENGTH(v.actWkdy) >= :weekday5 AND SUBSTRING(v.actWkdy, :weekday5, 1) = '1') AND "
            + "(:weekday6 IS NULL OR LENGTH(v.actWkdy) >= :weekday6 AND SUBSTRING(v.actWkdy, :weekday6, 1) = '1') AND "
            + "(:weekday7 IS NULL OR LENGTH(v.actWkdy) >= :weekday7 AND SUBSTRING(v.actWkdy, :weekday7, 1) = '1')")
    List<VolunteerActivity> findByWeekdays(
            @Param("weekday1") Integer weekday1,
            @Param("weekday2") Integer weekday2,
            @Param("weekday3") Integer weekday3,
            @Param("weekday4") Integer weekday4,
            @Param("weekday5") Integer weekday5,
            @Param("weekday6") Integer weekday6,
            @Param("weekday7") Integer weekday7
    );

    List<VolunteerActivity> findByProgrmBgndeBetween(LocalDate startDate, LocalDate endDate);

    List<VolunteerActivity> findByRcritNmpr(String keywordRn);

    List<VolunteerActivity> findByActBeginTm(Integer actBeginTm);

    List<VolunteerActivity> findByAdultPosblAtAndYngbgsPosblAt(String y, String n);

    List<VolunteerActivity> findByGrpPosblAt(String grpPosblAt);
}