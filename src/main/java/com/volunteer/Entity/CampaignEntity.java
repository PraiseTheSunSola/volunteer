package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="campaign_id")
    private Long id;

    private String campaignTitle;
    private String campaignItem;
    private String category;

    private Long adminId; // adminId 필드 추가

    // adminId의 getter와 setter
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
