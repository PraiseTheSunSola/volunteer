package com.volunteer.Control;

import com.volunteer.DTO.FindMemberDto;
import com.volunteer.DTO.MemberFormDto;
import com.volunteer.Entity.Member;
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

    //로그인 페이지 요청
    @GetMapping("/login")
    public String loginPage(Model model){
        return "member/login";
    }

    // 회원가입 페이지 요청
    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/join";
    }


    //회원가입 내용 저장 페이지
    @PostMapping("/join")
    public String join(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 폼 데이터 검증 실패 처리
            return "member/join";
        }

        // 비밀번호 확인 처리
        if (!memberFormDto.isPasswordConfirmed()) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지 않습니다.");
            return "member/join";
        }

        try {
            // PasswordEncoder를 memberService.saveMember()에 전달
            memberService.saveMember(memberFormDto, passwordEncoder);
        } catch (IllegalStateException e) {
            bindingResult.rejectValue("memberUserId", "error.memberFormDto", e.getMessage());
            return "member/join";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("memberEmail", "error.memberFormDto", e.getMessage());
            return "member/join";
        }

        return "redirect:/member/login";
    }

    //회원가입 실패 시(아이디나 비밀번호 틀릴 경우)
    @GetMapping("/signIn/Error")
    public String loginFail(Model model){
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "member/login";
    }

    //아이디 및 비밀번호 찾기 불러오기
    @GetMapping("/findIdAndPw")
    public String findIdAndPasswordPage(Model model) {
        if (!model.containsAttribute("findMemberDto")) {
            model.addAttribute("findMemberDto", new FindMemberDto());
        }
        return "member/findIdAndPw";
    }

    //아이디 및 비밀번호 찾기 실행
    @PostMapping("/findUserId/sendCode")
    public String findUserId(@Valid FindMemberDto findMemberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("findMemberDto", findMemberDto); // 에러가 발생했을 때도 모델에 객체를 추가
            return "member/findIdAndPw";
        }

        try {
            String verificationCode = memberService.sendVerificationCodeForUserId(findMemberDto);
            model.addAttribute("message", "인증 코드가 이메일로 발송되었습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "member/findIdAndPw";
    }

    @PostMapping("/findPassword/sendCode")
    public String findPassword(@Valid FindMemberDto findMemberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/findIdAndPw";
        }

        try {
            String verificationCode = memberService.sendVerificationCodeForPassword(findMemberDto);
            model.addAttribute("message", "인증 코드가 이메일로 발송되었습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "member/findIdAndPw";
    }

    @PostMapping("/findPassword/reset")
    public String resetPassword(@Valid FindMemberDto findMemberDto, @RequestParam String verificationCode, Model model) {
        try {
            String tempPassword = memberService.resetPasswordWithVerificationCode(findMemberDto, verificationCode, passwordEncoder);
            model.addAttribute("message", "임시 비밀번호가 발급되었습니다: " + tempPassword);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "member/findIdAndPw";
    }

    @GetMapping("/member/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "member/login"; // login.html로 이동
    }



}
