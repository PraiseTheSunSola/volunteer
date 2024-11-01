package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private int id;

    private String memberUserId;
    private String memberNickname;
    private String memberPassword;
    private String memberEmail;
    private String memberAddress;
    private int memberBirthdate;

}
