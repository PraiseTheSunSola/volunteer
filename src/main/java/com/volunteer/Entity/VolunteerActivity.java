package com.volunteer.Entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="VolunteerActivity")
public class VolunteerActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    // 요청 파라미터 (Request Parameter)
    @Column(unique = true, length = 10, nullable = false)
    private String progrmRegistNo; // 프로그램 등록 번호

    // 응답 요소 (Response Element)
    @Column(length = 7)
    private String actWkdy; // 활동 요일

    @Column(length = 7)
    private String appTotal; // 신청인원

    @Column(length = 50)
    private String srvcClCode; // 서비스 유형 코드

    @Column(length = 1)
    private String adultPosblAt; // 성인 가능 여부

    @Column(length = 1)
    private String yngbgsPosblAt; // 청소년 가능 여부

    @Column(length = 50)
    private String mnnstNm; // 모집 기관명

    @Column(length = 50)
    private String nanmmbyNm; // 등록 기관명

    @Column(length = 100)
    private String actPlace; // 봉사 장소

    @Column(length = 100)
    private String nanmmbyNmAdmn; // 담당자명

    @Column(length = 20)
    private String telno; // 전화번호

    @Column(length = 14)
    private String fxnum; // 팩스 번호

    @Column(length = 100)
    private String postAdres; // 주소

    @Column(length = 50)
    private String email; // 이메일

    @Column(length = 1000)
    private String progrmCn; // 프로그램 내용

    @Column(length = 4)
    private String resultCode; // 결과 코드

    @Column(length = 50)
    private String resultMsg; // 결과 메시지

    private int numOfRows; // 한 페이지 결과 수
    private int pageNo; // 페이지 번호
    private int totalCount; // 전체 결과 수

    @Column(length = 100)
    private String progrmSj; // 봉사 제목

    @Column(length = 100)
    private String progrmSttusSe; // 모집 상태

    @Column(length = 8)
    private String progrmBgnde; // 봉사 시작 일자

    @Column(length = 8)
    private String progrmEndde; // 봉사 종료 일자

    private int actBeginTm; // 봉사 시작 시간
    private int actEndTm; // 봉사 종료 시간

    @Column(length = 8)
    private String noticeBgnde; // 모집 시작 일자

    @Column(length = 8)
    private String noticeEndde; // 모집 종료 일자

    private int rcritNmpr; // 모집 인원

    @Column(length = 7)
    private String sidoCd; // 시도 코드

    @Column(length = 7)
    private String gugunCd; // 시군구 코드

}
