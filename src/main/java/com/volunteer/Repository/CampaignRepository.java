package com.volunteer.Repository;

import com.volunteer.Entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    // 특정 카테고리의 캠페인 조회
    List<CampaignEntity> findByCategory(String category);

    // 카테고리별 오름차순 캠페인 조회
    List<CampaignEntity> findAllByOrderByCategoryAsc();
}
