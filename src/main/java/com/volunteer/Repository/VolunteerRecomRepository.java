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
    List<VolunteerActivity> findByProgrmBgnDeBetween(String startDate, String endDate);
    List<VolunteerActivity> findByRcritNmpr(String keywordRn);
}
