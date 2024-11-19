package com.volunteer.DTO;

import com.volunteer.Constant.Role;
import com.volunteer.Entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberFormDto {
    private Long memberFormId;

    @NotBlank(message = "아이디를 입력해주세요")
    private String memberUserId;

    private String memberName;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$",
            message = "비밀번호는 영어 소문자, 숫자, 특수문자를 포함한 6~12자로 입력해주세요"
    )
    private String memberPassword;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요.")
    private String confirmPassword;
    private Role role; // 관리자가 될지 여부

    public boolean isPasswordConfirmed() {
        return memberPassword != null && confirmPassword != null && memberPassword.equals(confirmPassword);
    }

    @NotBlank(message = "이메일을 작성해주세요")
    private String memberEmail;

    private String memberNickname;
    private String memberAddress;
    private String memberBirthdate;
    private Boolean memberIsAdult;

    // Dto > Entity 변환
    public Member createEntity(PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setMemberUserId(this.memberUserId);
        member.setMemberName(this.memberName);
        member.setMemberEmail(this.memberEmail);
        member.setMemberAddress(this.memberAddress);
        member.setMemberNickname(this.memberNickname);
        member.setMemberBirthdate(LocalDate.parse(this.memberBirthdate));
        member.setMemberIsAdult(this.memberIsAdult);

        // 기본 역할은 USER, admin을 선택하면 ADMIN으로 설정
        if (this.isAdmin()) {
            member.setRole(Role.ADMIN);
        } else {
            member.setRole(Role.USER);
        }

        // 비밀번호는 암호화해서 저장
        String pw = passwordEncoder.encode(this.memberPassword);
        member.setMemberPassword(pw);

        return member;
    }

    // Entity > Dto 변환
    public static MemberFormDto of(Member member) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setMemberUserId(member.getMemberUserId());
        memberFormDto.setMemberName(member.getMemberName());
        memberFormDto.setMemberEmail(member.getMemberEmail());
        memberFormDto.setMemberAddress(member.getMemberAddress());
        memberFormDto.setMemberNickname(member.getMemberNickname());
        memberFormDto.setMemberIsAdult(member.isMemberIsAdult());
        memberFormDto.setMemberBirthdate(String.valueOf(member.getMemberBirthdate()));
        memberFormDto.setRole(member.getRole());  // Role 값도 설정

        return memberFormDto;
    }

    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role); // ROLE_ADMIN이면 true 반환
    }
}
