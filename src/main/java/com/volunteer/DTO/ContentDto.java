package com.volunteer.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContentDto {
    private long id;

    private String title;
    private String category;
    private LocalDateTime recruitStart;
    private LocalDateTime recruitEnd;
    private int goalCost;
    private LocalDateTime activityStart;
    private LocalDateTime activityEnd;
    private String age;
    private String group;
    private int recruitCount;
    private String agencyName;
    private long agencyTel;
    private String agencyEmail;
    private String content;
}
