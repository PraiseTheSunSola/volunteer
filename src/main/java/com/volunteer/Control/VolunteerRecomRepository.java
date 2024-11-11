package com.volunteer.Control;

import com.volunteer.Entity.VolunteerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VolunteerRecomRepository extends JpaRepository<VolunteerActivity, Long> {
    List<VolunteerActivity> findByProgrmSjContaining(String keyword);
}
