package com.volunteer.DTO;

import com.volunteer.Constant.Role;
import com.volunteer.Entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class MemberFormDto {
    private Long memberFormId;

    @NotBlank(message="아이디를 입력해주세요")
    private String memberUserId;

    @NotBlank(message="비밀번호를 입력해주세요")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$",
            message = "비밀번호는 영어 소문자, 숫자, 특수문자를 포함한 6~12자로 입력해주세요"
    )
    private String memberPassword;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요.")
    private String confirmPassword;
    public boolean isPasswordConfirmed() {
        return memberPassword.equals(confirmPassword);
    }

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
