package com.volunteer.Service;

import com.volunteer.DTO.CampaignDto;
import com.volunteer.Entity.CampaignEntity;
import com.volunteer.Repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;

    // 캠페인 글 저장
    public void writeSave(String title, String item, String category) {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignTitle(title);
        campaignEntity.setCampaignItem(item);
        campaignEntity.setCategory(category);
        // adminId가 필요하다면 여기서 설정합니다.
        campaignEntity.setAdminId(1L);  // 예시로 1L을 설정
        campaignRepository.save(campaignEntity); // DB에 캠페인 저장
    }

    // 모든 캠페인 리스트 가져오기
    public List<CampaignDto> getAllCampaigns() {
        return campaignRepository.findAll().stream()
                .map(campaignEntity -> new CampaignDto(
                        campaignEntity.getCampaignTitle(),
                        campaignEntity.getCampaignItem(),
                        campaignEntity.getAdminId(),  // adminId를 포함하여 DTO 생성
                        campaignEntity.getCategory()))  // category를 포함하여 DTO 생성
                .collect(Collectors.toList());
    }

    // 카테고리별 캠페인 가져오기
    public List<CampaignDto> getCampaignsByCategory(String category) {
        return campaignRepository.findByCategory(category).stream()
                .map(campaignEntity -> new CampaignDto(
                        campaignEntity.getCampaignTitle(),
                        campaignEntity.getCampaignItem(),
                        campaignEntity.getAdminId(),  // adminId를 포함하여 DTO 생성
                        campaignEntity.getCategory()))  // category를 포함하여 DTO 생성
                .collect(Collectors.toList());
    }
}
