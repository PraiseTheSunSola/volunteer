package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ParticipationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "participation_id")
    private Long id; // 참여 고유 ID

    private String participationTitle;
    private String participationItem;
    private String category;
    private LocalDateTime participationStart;
    private LocalDateTime participationEnd;

    private Long adminId; // adminId 필드 추가

    // LocalDateTime을 Instant로 변환하는 메소드
    public LocalDateTime getEndDate() {
        return participationEnd; // LocalDateTime 반환
    }
}
