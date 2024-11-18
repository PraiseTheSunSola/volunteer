package com.volunteer.Service;

import com.volunteer.DTO.CampaignDto;
import com.volunteer.Entity.CampaignEntity;
import com.volunteer.Repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;

    // 캠페인 글 저장
    public void writeSave(String title, String item, String category,
                          LocalDateTime startDate, LocalDateTime endDate) {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignTitle(title);
        campaignEntity.setCampaignItem(item);
        campaignEntity.setCategory(category);
        campaignEntity.setCampaignStart(startDate);
        campaignEntity.setCampaignEnd(endDate);

        // 하드코딩으로 adminId를 1L로 설정 (나중에 관리자 ID 추가시 변경 예정)
        campaignEntity.setAdminId(1L);

        campaignRepository.save(campaignEntity);
    }

    // 캠페인 수정
    public void updateCampaign(Long id, String title, String item, String category,
                               LocalDateTime startDate, LocalDateTime endDate) {
        CampaignEntity campaignEntity = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("캠페인을 찾을 수 없습니다."));

        // 값 업데이트
        campaignEntity.setCampaignTitle(title);
        campaignEntity.setCampaignItem(item);
        campaignEntity.setCategory(category);
        campaignEntity.setCampaignStart(startDate);
        campaignEntity.setCampaignEnd(endDate);

        campaignRepository.save(campaignEntity); // 업데이트 저장
    }

    // 모든 캠페인 리스트 가져오기 (카테고리 순으로 정렬)
    public List<CampaignDto> getAllCampaigns() {
        return campaignRepository.findAllByOrderByCategoryAsc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // CampaignEntity를 CampaignDto로 변환하는 헬퍼 메서드
    private CampaignDto convertToDto(CampaignEntity campaignEntity) {
        return new CampaignDto(
                campaignEntity.getId(),
                campaignEntity.getCampaignTitle(),
                campaignEntity.getCampaignItem(),
                campaignEntity.getAdminId(),
                campaignEntity.getCategory(),
                campaignEntity.getCampaignStart(),
                campaignEntity.getCampaignEnd()
        );
    }

    // 캠페인 상세 조회
    public CampaignDto getCampaignById(Long id) {
        CampaignEntity campaignEntity = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("캠페인을 찾을 수 없습니다."));
        return convertToDto(campaignEntity);
    }
}
