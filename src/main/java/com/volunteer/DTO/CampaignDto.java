package com.volunteer.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignDto {
    private String campaignTitle;
    private String campaignItem;
    private Long adminId; // 관리자 정보
}
