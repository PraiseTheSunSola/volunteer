package com.volunteer.Repository;

import com.volunteer.Entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    // 카테고리별 캠페인 조회
    List<CampaignEntity> findByCategory(String category);

    // 추가적인 쿼리 메서드가 필요하면 작성
}
