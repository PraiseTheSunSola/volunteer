package com.volunteer.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FindMemberDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "생년월일을 입력해주세요.")
    private String birthdate;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    // 비밀번호 찾기를 위한 아이디 필드
    private String userId;
}
