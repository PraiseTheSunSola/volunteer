package com.volunteer.Repository;

import com.volunteer.Entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    // 추가적인 쿼리 메서드가 필요하다면 여기서 작성
}
