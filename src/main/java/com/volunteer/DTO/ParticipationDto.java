package com.volunteer.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParticipationDto {
    private Long id; // 참여의 고유 ID
    private String participationTitle;
    private String participationItem;
    private Long adminId; // 관리자 정보
    private String category;
    private LocalDateTime participationStart; // 수정된 필드명
    private LocalDateTime participationEnd;   // 수정된 필드명

    // 모든 필드를 초기화하는 생성자
    public ParticipationDto(Long id, String participationTitle, String participationItem, Long adminId, String category,
                            LocalDateTime participationStart, LocalDateTime participationEnd) {
        this.id = id;
        this.participationTitle = participationTitle;
        this.participationItem = participationItem;
        this.adminId = adminId;
        this.category = category;
        this.participationStart = participationStart;
        this.participationEnd = participationEnd;
    }

    // 카테고리와 관리자 정보를 제외한 기본 생성자
    public ParticipationDto(Long id, String participationTitle, String participationItem, String category,
                            LocalDateTime participationStart, LocalDateTime participationEnd) {
        this.id = id;
        this.participationTitle = participationTitle;
        this.participationItem = participationItem;
        this.category = category;
        this.participationStart = participationStart;
        this.participationEnd = participationEnd;
    }

    // 관리자를 제외한 생성자
    public ParticipationDto(String participationTitle, String participationItem, String category,
                            LocalDateTime participationStart, LocalDateTime participationEnd) {
        this.participationTitle = participationTitle;
        this.participationItem = participationItem;
        this.category = category;
        this.participationStart = participationStart;
        this.participationEnd = participationEnd;
    }

    public ParticipationDto() {

    }

    // 참여의 종료일을 Instant로 반환하는 메서드
    public LocalDateTime getEndDate() {
        return participationEnd; // LocalDateTime을 그대로 반환
    }
}
