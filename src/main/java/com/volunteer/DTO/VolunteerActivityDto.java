package com.volunteer.DTO;

import com.volunteer.Entity.Member;
import com.volunteer.Entity.VolunteerActivity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerActivityDto {
    private String progrmRegistNo; // 프로그램 등록 번호
    private String actWkdy; // 활동 요일
    private String appTotal; // 신청인원
    private String srvcClCode; // 서비스 유형 코드
    private String adultPosblAt; // 성인 가능 여부
    private String yngbgsPosblAt; // 청소년 가능 여부
    private String mnmstNm; // 모집 기관명
    private String nanmmbyNm; // 등록 기관명
    private String actPlace; // 봉사 장소
    private String nanmmbyNmAdmn; // 담당자명
    private String telno; // 전화번호
    private String fxnum; // 팩스 번호
    private String postAdres; // 주소
    private String email; // 이메일
    private String progrmCn; // 프로그램 내용
    private String progrmSj; // 봉사 제목
    private String progrmSttusSe; // 모집 상태
    private String progrmBgnde; // 봉사 시작 일자
    private String progrmEndde; // 봉사 종료 일자
    private int actBeginTm; // 봉사 시작 시간
    private int actEndTm; // 봉사 종료 시간
    private String noticeBgnde; // 모집 시작 일자
    private String noticeEndde; // 모집 종료 일자
    private int rcritNmpr; // 모집 인원
    private String sidoCd; // 시도 코드
    private String gugunCd; // 시군구 코드
    private Member member;   //회원정보

    //DTO -> Entity
    public VolunteerActivity createEntity() {
        VolunteerActivity entity = new VolunteerActivity();
        entity.setProgrmRegistNo(this.progrmRegistNo);
        entity.setActWkdy(this.actWkdy);
        entity.setAppTotal(this.appTotal);
        entity.setSrvcClCode(this.srvcClCode);
        entity.setAdultPosblAt(this.adultPosblAt);
        entity.setYngbgsPosblAt(this.yngbgsPosblAt);
        entity.setMnmstNm(this.mnmstNm);
        entity.setActPlace(this.actPlace);
        entity.setNanmmbyNmAdmn(this.nanmmbyNmAdmn);
        entity.setTelno(this.telno);
        entity.setFxnum(this.fxnum);
        entity.setPostAdres(this.postAdres);
        entity.setEmail(this.email);
        entity.setProgrmCn(this.progrmCn);
        entity.setProgrmSj(this.progrmSj);
        entity.setProgrmSttusSe(this.progrmSttusSe);
        entity.setProgrmBgnde(this.progrmBgnde);
        entity.setProgrmEndde(this.progrmEndde);
        entity.setActBeginTm(this.actBeginTm);
        entity.setNoticeBgnde(this.noticeBgnde);
        entity.setNoticeEndde(this.noticeEndde);
        entity.setRcritNmpr(this.rcritNmpr);
        entity.setSidoCd(this.sidoCd);
        entity.setGugunCd(this.gugunCd);
        entity.setMember(this.member);

        return entity;
    }

// Entity -> DTO
    public static VolunteerActivityDto of(VolunteerActivity entity) {
        VolunteerActivityDto volunteerActivityDto = new VolunteerActivityDto();
        volunteerActivityDto.setProgrmRegistNo(entity.getProgrmRegistNo());
        volunteerActivityDto.setActWkdy(entity.getActWkdy());
        volunteerActivityDto.setAppTotal(entity.getAppTotal());
        volunteerActivityDto.setSrvcClCode(entity.getSrvcClCode());
        volunteerActivityDto.setAdultPosblAt(entity.getAdultPosblAt());
        volunteerActivityDto.setYngbgsPosblAt(entity.getYngbgsPosblAt());
        volunteerActivityDto.setMnmstNm(entity.getMnmstNm());
        volunteerActivityDto.setActPlace(entity.getActPlace());
        volunteerActivityDto.setNanmmbyNmAdmn(entity.getNanmmbyNmAdmn());
        volunteerActivityDto.setTelno(entity.getTelno());
        volunteerActivityDto.setFxnum(entity.getFxnum());
        volunteerActivityDto.setPostAdres(entity.getPostAdres());
        volunteerActivityDto.setEmail(entity.getEmail());
        volunteerActivityDto.setProgrmCn(entity.getProgrmCn());
        volunteerActivityDto.setProgrmSj(entity.getProgrmSj());
        volunteerActivityDto.setProgrmSttusSe(entity.getProgrmSttusSe());
        volunteerActivityDto.setProgrmBgnde(entity.getProgrmBgnde());
        volunteerActivityDto.setProgrmEndde(entity.getProgrmEndde());
        volunteerActivityDto.setActBeginTm(entity.getActBeginTm());
        volunteerActivityDto.setNoticeBgnde(entity.getNoticeBgnde());
        volunteerActivityDto.setNoticeEndde(entity.getNoticeEndde());
        volunteerActivityDto.setRcritNmpr(entity.getRcritNmpr());
        volunteerActivityDto.setSidoCd(entity.getSidoCd());
        volunteerActivityDto.setGugunCd(entity.getGugunCd());
        volunteerActivityDto.setMember(entity.getMember());

        return volunteerActivityDto;
    }
}

