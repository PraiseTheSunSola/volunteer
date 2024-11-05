package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Getter
@Setter
@Table(name = "Volunteer_participation_details")
public class VolunteerParticipationDetails {

    @Id
    @Column(name = "program_id", length = 10)
    private String programId;

    //VolunteerParticipation과 연결
    @OneToOne
    @JoinColumn(name = "program_id", referencedColumnName = "program_id", insertable = false, updatable = false)
    private VolunteerParticipation volunteerParticipation;

    @Column(name = "recruitment_capacity")
    private int recruitmentCapacity;

    @Column(name = "activity_weekdays", length = 7)
    private String activityWeekdays;

    @Column(name = "application_count")
    private int applicationCount;

    @Column(name = "group_available", length = 1)
    private char groupAvailable;

    @Column(name = "main_organization", length = 50)
    private String mainOrganization;

    @Column(name = "registering_organization", length = 50)
    private String registeringOrganization;

    @Column(name = "contact_name", length = 50)
    private String contactName;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    @Column(name = "contact_fax", length = 14)
    private String contactFax;

    @Column(name = "contact_address", length = 100)
    private String contactAddress;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}

