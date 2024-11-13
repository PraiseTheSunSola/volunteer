package com.volunteer.Service;

import com.volunteer.Entity.CampaignEntity;
import com.volunteer.Repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;

    // 캠페인 글 저장
    public void writeSave(String title, String item) {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCampaignTitle(title);
        campaignEntity.setCampaignItem(item);
        campaignRepository.save(campaignEntity); // DB에 캠페인 저장
    }

    // 모든 캠페인 리스트 가져오기
    public List<CampaignEntity> getAllCampaigns() {
        return campaignRepository.findAll();  // 모든 캠페인 리스트를 반환
    }
}
