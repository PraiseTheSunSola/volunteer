package com.volunteer.Repository;

import com.volunteer.Entity.VolunteerParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerParticipationRepository extends JpaRepository<VolunteerParticipation, String> {
}
