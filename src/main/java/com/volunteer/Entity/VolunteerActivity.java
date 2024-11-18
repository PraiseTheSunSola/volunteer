package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="volunteer_activity")
public class VolunteerActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID; // 기본 키

    @Column(name = "progrmRegistNo", unique = true, nullable = false)
    private Integer progrmRegistNo; // 프로그램 등록 번호

    @Column
    private Integer actWkdy; // 활동 요일

    @Column
    private Integer appTotal; // 신청인원

    @Column
    private Integer srvcClCode; // 서비스 유형 코드

    @Column(length = 50)
    private String adultPosblAt; // 성인 가능 여부

    @Column(length = 50)
    private String yngbgsPosblAt; // 청소년 가능 여부

    @Column(length = 50)
    private String familyPosblAt; // 가족 가능 여부

    @Column(length = 50)
    private String pbsvntPosblAt; // CSV 파일 기준 존재하는 항목

    @Column(length = 50)
    private String grpPosblAt; // 단체 가능 여부

    @Column(length = 50)
    private String mnnstNm; // 모집 기관명

    @Column(length = 50)
    private String nanmmbyNm; // 등록 기관명

    @Column(columnDefinition = "TEXT")
    private String actPlace; // 봉사 장소

    @Column(length = 50)
    private String nanmmbyNmAdmn; // 담당자명

    @Column(length = 50)
    private String telno; // 전화번호

    @Column(length = 50)
    private String fxnum; // 팩스 번호

    @Column(length = 128)
    private String postAdres; // 주소

    @Column(columnDefinition = "TEXT")
    private String email; // 이메일

    @Lob
    @Column
    private String progrmCn; // 프로그램 내용

    @Column(columnDefinition = "TEXT")
    private String progrmSj; // 봉사 제목

    @Column
    private Integer progrmSttusSe; // 모집 상태

    @Column
    private LocalDate progrmBgnde; // 봉사 시작 일자

    @Column
    private LocalDate progrmEndde; // 봉사 종료 일자

    private Integer actBeginTm; // 봉사 시작 시간
    private Integer actEndTm; // 봉사 종료 시간

    @Column
    private Integer noticeBgnde; // 모집 시작 일자

    @Column
    private Integer noticeEndde; // 모집 종료 일자

    private Integer rcritNmpr; // 모집 인원

    @Column
    private Integer sidoCd; // 시도 코드

    @Column
    private Integer gugunCd; // 시군구 코드

    @Column(name = "area_lalo1", columnDefinition = "TEXT")
    private String areaLalo1;

    @Column(name = "area_lalo2", columnDefinition = "TEXT")
    private String areaLalo2;

    @Column(name = "area_lalo3", columnDefinition = "TEXT")
    private String areaLalo3;

    @Column(name = "area_address1", columnDefinition = "TEXT")
    private String areaAddress1;

    @Column(name = "area_address2", columnDefinition = "TEXT")
    private String areaAddress2;

    @Column(name = "area_address3", columnDefinition = "TEXT")
    private String areaAddress3;

}
