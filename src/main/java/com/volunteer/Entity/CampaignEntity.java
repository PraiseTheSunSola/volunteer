package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class CampaignEntity {

    @Id
    @Column(name = "admin_board_id")
    private Long id;



}
