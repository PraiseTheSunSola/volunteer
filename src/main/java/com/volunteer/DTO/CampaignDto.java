package com.volunteer.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignDto {
    private String campaignTitle;
    private String campaignItem;
    private Long adminId; // 관리자 정보
    private String category;

    // CampaignDto의 생성자 추가
    public CampaignDto(String campaignTitle, String campaignItem, Long adminId, String category) {
        this.campaignTitle = campaignTitle;
        this.campaignItem = campaignItem;
        this.adminId = adminId;
        this.category = category;
    }
}
