package com.volunteer.Service;

import com.volunteer.DTO.CampaignDto;
import com.volunteer.Entity.CampaignEntity;
import com.volunteer.Repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
        campaignEntity.setAdminId(1L);  // 관리자 ID를 1L로 하드코딩

        campaignRepository.save(campaignEntity); // DB에 캠페인 저장
    }

    // 모든 캠페인 리스트 가져오기 (카테고리 순으로 정렬)
    public List<CampaignDto> getAllCampaigns() {
        return campaignRepository.findAllByOrderByCategoryAsc().stream()
                .map(this::convertToDto)  // CampaignEntity를 CampaignDto로 변환
                .collect(Collectors.toList());
    }


    // 카테고리별 캠페인 분류 및 모델 추가
    public void getAllCampaignsWithCategories(Model model) {
        List<CampaignEntity> campaignEntities = campaignRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        // 카테고리별로 캠페인 분류
        model.addAttribute("signCampaigns", filterCampaignsByCategory(campaignEntities, "sign", now, true));
        model.addAttribute("joinCampaigns", filterCampaignsByCategory(campaignEntities, "join", now, true));
        model.addAttribute("lastCampaigns", filterCampaignsByCategory(campaignEntities, "last", now, false));
    }

    // 카테고리와 종료 여부를 고려한 캠페인 필터링
    private List<CampaignDto> filterCampaignsByCategory(List<CampaignEntity> campaigns, String category, LocalDateTime now, boolean isFuture) {
        return campaigns.stream()
                .filter(campaign -> category.equals(campaign.getCategory()) &&
                        (campaign.getCampaignEnd() != null && // 종료일이 null이 아닌 경우에만 비교
                                (isFuture ? campaign.getCampaignEnd().isAfter(now) : campaign.getCampaignEnd().isBefore(now))))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // CampaignEntity를 CampaignDto로 변환하는 헬퍼 메서드
    private CampaignDto convertToDto(CampaignEntity campaignEntity) {
        return new CampaignDto(
                campaignEntity.getId(),
                campaignEntity.getCampaignTitle(),
                campaignEntity.getCampaignItem(),
                campaignEntity.getAdminId(),  // adminId를 그대로 반환
                campaignEntity.getCategory(),
                campaignEntity.getCampaignStart(),
                campaignEntity.getCampaignEnd() // 캠페인 종료일을 LocalDateTime 그대로 반환
        );
    }

    // 카테고리별 캠페인 가져오기
    public List<CampaignDto> getCampaignsByCategory(String category) {
        LocalDateTime now = LocalDateTime.now();

        return campaignRepository.findByCategory(category).stream()
                .filter(campaign -> {
                    if (campaign.getCampaignEnd() == null) {
                        return false; // 종료일이 null인 캠페인은 제외
                    }
                    if ("last".equals(category)) {
                        return campaign.getCampaignEnd().isBefore(now);  // 종료일이 현재보다 이전인 캠페인만
                    } else {
                        return campaign.getCampaignEnd().isAfter(now);  // 종료일이 현재보다 이후인 캠페인만
                    }
                })
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 캠페인 상세 조회
    public CampaignDto getCampaignById(Long id) {
        CampaignEntity campaignEntity = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("캠페인을 찾을 수 없습니다."));
        return convertToDto(campaignEntity);
    }
}
