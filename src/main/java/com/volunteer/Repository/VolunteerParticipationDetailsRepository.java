package com.volunteer.Repository;

import com.volunteer.Entity.VolunteerParticipationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerParticipationDetailsRepository extends JpaRepository<VolunteerParticipationDetails, String> {
}
