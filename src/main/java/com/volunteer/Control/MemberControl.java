package com.volunteer.Control;

import com.volunteer.DTO.MemberFormDto;
import com.volunteer.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberControl {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //회원가입 페이지 요청
    @GetMapping("/signIn")
    public String loginPage(Model model){
        return "member/login";
    }

    //회원가입 내용 저장 페이지
    @PostMapping("/signUp")
    public String join(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        // 비밀번호 확인 처리
        if (!memberFormDto.isPasswordConfirmed()) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지 않습니다.");
            return "member/join";
        }

        try {
            memberService.saveMember(memberFormDto, passwordEncoder);
        } catch (IllegalStateException e1) {
            bindingResult.rejectValue("memberUserId", "error.memberFormDto", e1.getMessage());
            return "member/join";
        } catch (IllegalArgumentException e2) {
            bindingResult.rejectValue("memberEmail", "error.memberFormDto", e2.getMessage());
            return "member/join";
        }

        return "redirect:/member/signIn";
    }

    //회원가입 실패 시(아이디나 비밀번호 틀릴 경우)
    @GetMapping("/signIn/Error")
    public String loginFail(Model model){
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "member/login";
    }

    @PostMapping("/findUserId/sendCode")
    public String sendUserIdVerificationCode(
            @RequestParam String name,
            @RequestParam String birthdate,
            @RequestParam String email,
            Model model) {
        try {
            // 인증 코드 생성 및 이메일 발송
            String verificationCode = memberService.sendVerificationCodeForUserId(name, birthdate, email);
            model.addAttribute("message", "인증 코드가 이메일로 발송되었습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "member/findIdAndPw"; // 데이터 재바인딩을 위해 같은 폼으로 리턴
    }

    // 비밀번호 찾기 요청 처리
    @PostMapping("/findPassword")
    public String findPassword(
            @RequestParam String userId,
            @RequestParam String name,
            @RequestParam String email,
            Model model) {
        try {
            String tempPassword = memberService.resetPassword(userId, name, email);
            model.addAttribute("message", "임시 비밀번호가 이메일로 발송되었습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "member/findIdAndPw"; // 데이터 재바인딩을 위해 같은 폼으로 리턴
    }





}
