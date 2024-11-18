package com.volunteer.Repository;

import com.volunteer.Entity.ParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<ParticipationEntity, Long> {

    // 카테고리별로 참여를 찾기
    List<ParticipationEntity> findByCategory(String category);

    // 모든 참여를 카테고리순으로 정렬
    List<ParticipationEntity> findAllByOrderByCategoryAsc();
}
