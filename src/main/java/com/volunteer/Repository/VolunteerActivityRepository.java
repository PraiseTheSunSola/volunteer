package com.volunteer.Repository;

import com.volunteer.Entity.VolunteerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerActivityRepository extends JpaRepository<VolunteerActivity, Long> {
    boolean existsByProgrmRegistNo(String progrmRegistNo);
}
