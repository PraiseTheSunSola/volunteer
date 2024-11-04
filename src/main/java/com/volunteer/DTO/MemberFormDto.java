package com.volunteer.DTO;

import com.volunteer.Constant.Role;
import com.volunteer.Entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class MemberFormDto {
    private Long memberFormId;

    //@NotBlank(message="이 안에 아이디 설정 규칙을 넣고 주석해제 해주세요")
    private String memberUserId;

    //@NotBlank(message="이 안에 비밀번호 설정 규칙을 넣고 주석해제 해주세요")
    private String memberPassword;

    @NotBlank(message = "이메일을 작성해주세요")
    private String memberEmail;

    private String memberNickname;
    private String memberAddress;
    private LocalDate memberBirthdate;

    //Dto > Entity
    public Member createEntity(PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setMemberUserId(this.memberUserId);
        member.setMemberEmail(this.memberEmail);
        member.setMemberAddress(this.memberAddress);
        member.setMemberNickname(this.memberNickname);
        member.setMemberBirthdate(this.memberBirthdate);
        member.setRole(Role.USER);

        String pw = passwordEncoder.encode(this.memberPassword);
        member.setMemberPassword(pw);

        return member;
    }

    //Entity > Dto
    public static MemberFormDto of(Member member){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setMemberUserId(member.getMemberUserId());
        memberFormDto.setMemberEmail(member.getMemberEmail());
        memberFormDto.setMemberAddress(member.getMemberAddress());
        memberFormDto.setMemberNickname(member.getMemberNickname());
        memberFormDto.setMemberBirthdate(member.getMemberBirthdate());

        return memberFormDto;
    }


}
