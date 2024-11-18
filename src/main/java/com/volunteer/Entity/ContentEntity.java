package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="admin_content")
public class ContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id")
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
    private String agencyTel;
    private String agencyEmail;
    private String content;

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime created;
}
