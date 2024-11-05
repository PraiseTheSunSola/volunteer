package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "Volunteer_participation")
public class VolunteerParticipation {

    @Id
    @Column(name = "program_id", length = 10)
    private String programId;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "organization", length = 50)
    private String organization;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    //Location과 연결
    @ManyToOne
    @JoinColumn(name = "province_code")
    private Location location;

    @Column(name = "adult_available", length = 1)
    private char adultAvailable;

    @Column(name = "youth_available", length = 1)
    private char youthAvailable;

    @Column(name = "service_category", length = 50)
    private String serviceCategory;

    @Column(name = "activity_place", length = 50)
    private String activityPlace;

    @Column(name = "activity_start_time")
    private LocalTime activityStartTime;

    @Column(name = "activity_end_time")
    private LocalTime activityEndTime;

    @Column(name = "notice_start_date")
    private LocalDate noticeStartDate;

    @Column(name = "notice_end_date")
    private LocalDate noticeEndDate;

    @Column(name = "details_url", length = 255)
    private String detailsUrl;
}
