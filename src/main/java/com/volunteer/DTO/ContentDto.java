package com.volunteer.DTO;

import com.volunteer.Entity.ContentEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

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
    private String[] group;
    private int recruitCount;
    private String agencyName;
    private String agencyTel;
    private String agencyEmail;
    private String content;
    private LocalDateTime created;

    private static ModelMapper mapper=new ModelMapper();

    //DTO -> Entity
    public ContentEntity createEntity() {
        return mapper.map(this, ContentEntity.class);
    }

    //Entity -> DTO
    public static ContentDto of(ContentEntity contentEntity) {
        return mapper.map(contentEntity, ContentDto.class);
    }
}
