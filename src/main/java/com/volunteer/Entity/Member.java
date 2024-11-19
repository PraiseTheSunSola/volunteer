package com.volunteer.Entity;

import com.volunteer.Constant.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String memberUserId;
    private String memberName;
    private String memberNickname;
    private String memberPassword;
    private String memberEmail;
    private String memberAddress;
    private LocalDate memberBirthdate;
    private boolean memberIsAdult;

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    @Column(name = "role", nullable = false)
    private Role role; // USER 또는 ADMIN을 저장
}
