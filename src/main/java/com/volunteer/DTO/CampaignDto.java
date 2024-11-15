package com.volunteer.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CampaignDto {
    private Long id; // 캠페인의 고유 ID
    private String campaignTitle;
    private String campaignItem;
    private Long adminId; // 관리자 정보
    private String category;
    private LocalDateTime campaignStart; // 수정된 필드명
    private LocalDateTime campaignEnd;   // 수정된 필드명

    // 모든 필드를 초기화하는 생성자
    public CampaignDto(Long id, String campaignTitle, String campaignItem, Long adminId, String category,
                       LocalDateTime campaignStart, LocalDateTime campaignEnd) {
        this.id = id;
        this.campaignTitle = campaignTitle;
        this.campaignItem = campaignItem;
        this.adminId = adminId;
        this.category = category;
        this.campaignStart = campaignStart;
        this.campaignEnd = campaignEnd;
    }

    // 카테고리와 관리자 정보를 제외한 기본 생성자
    public CampaignDto(Long id, String campaignTitle, String campaignItem, String category,
                       LocalDateTime campaignStart, LocalDateTime campaignEnd) {
        this.id = id;
        this.campaignTitle = campaignTitle;
        this.campaignItem = campaignItem;
        this.category = category;
        this.campaignStart = campaignStart;
        this.campaignEnd = campaignEnd;
    }

    // 관리자를 제외한 생성자
    public CampaignDto(String campaignTitle, String campaignItem, String category,
                       LocalDateTime campaignStart, LocalDateTime campaignEnd) {
        this.campaignTitle = campaignTitle;
        this.campaignItem = campaignItem;
        this.category = category;
        this.campaignStart = campaignStart;
        this.campaignEnd = campaignEnd;
    }

    // 캠페인의 종료일을 Instant로 반환하는 메서드
    public LocalDateTime getEndDate() {
        return campaignEnd; // LocalDateTime을 그대로 반환
    }
}
