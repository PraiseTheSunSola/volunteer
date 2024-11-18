package com.volunteer.DTO;

import com.volunteer.Entity.ContentEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContentDto {
    private long id;

    private String title;
    private String category;
    private String recruitStart;
    private String recruitEnd;
    private int goalCost;
    private String activityStart;
    private String activityEnd;
    private String age;
    private String[] group;
    private int recruitCount;
    private String agencyName;
    private String agencyTel;
    private String agencyEmail;
    private String content;
    private String created;


    //DTO -> Entity
    public ContentEntity createEntity() {
        ContentEntity contentEntity = new ContentEntity();

        contentEntity.setTitle(this.title);
        contentEntity.setCategory(this.category);
        contentEntity.setRecruitStart(LocalDateTime.parse(this.recruitStart));
        contentEntity.setRecruitEnd(LocalDateTime.parse(this.recruitEnd));
        contentEntity.setContent(this.content);
        contentEntity.setActivityStart(LocalDate.parse(this.activityStart));
        contentEntity.setActivityEnd(LocalDate.parse(this.activityEnd));
        contentEntity.setAge(this.age);
        contentEntity.setGoalCost(this.goalCost);
        contentEntity.setGroup(this.group[0]);
        contentEntity.setAgencyName(this.agencyName);
        contentEntity.setAgencyTel(this.agencyTel);
        contentEntity.setAgencyEmail(this.agencyEmail);
        contentEntity.setCreated(LocalDateTime.now());

        return contentEntity;
    }

    //Entity -> DTO
    public static ContentDto of(ContentEntity contentEntity) {
        ContentDto contentDto = new ContentDto();

        contentDto.setTitle(contentEntity.getTitle());
        contentDto.setCategory(contentEntity.getCategory());
        contentDto.setContent(contentEntity.getContent());
        contentDto.setAgencyName(contentEntity.getAgencyName());
        contentDto.setAgencyTel(contentEntity.getAgencyTel());
        contentDto.setAgencyEmail(contentEntity.getAgencyEmail());
        contentDto.setAge(contentEntity.getAge());
        contentDto.setGoalCost(contentEntity.getGoalCost());
        contentDto.setGroup(contentDto.getGroup());
        contentDto.setRecruitCount(contentEntity.getRecruitCount());
        contentDto.setRecruitStart(contentEntity.getRecruitStart().toString());
        contentDto.setRecruitEnd(contentEntity.getRecruitEnd().toString());
        contentDto.setActivityStart(contentEntity.getActivityStart().toString());
        contentDto.setActivityEnd(contentEntity.getActivityEnd().toString());
        contentDto.setCreated(contentEntity.getCreated().toString());

        return contentDto;
    }
}
