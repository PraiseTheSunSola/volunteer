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

   // private Long adminId;  // 글 작성자의 관리자 ID



}
