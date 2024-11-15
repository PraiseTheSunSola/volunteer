package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "campaign_id")
    private Long id; // 캠페인 고유 ID

    private String campaignTitle;
    private String campaignItem;
    private String category;
    private LocalDateTime campaignStart;
    private LocalDateTime campaignEnd;

    private Long adminId; // adminId 필드 추가

    // LocalDateTime을 Instant로 변환하는 메소드
    public LocalDateTime getEndDate() {
        return campaignEnd; // LocalDateTime 반환
    }
}
