package com.volunteer.Entity;

import com.volunteer.Constant.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String memberUserId;
    private String memberNickname;
    private String memberPassword;
    private String memberEmail;
    private String memberAddress;
    private LocalDate memberBirthdate;
    private boolean memberIsAdult;


    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VolunteerActivity> volunteerActivities; // 회원이 신청한 봉사활동 목록
}
