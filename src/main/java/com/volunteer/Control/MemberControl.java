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
    public String join(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){      //값이 유효하지 않을 때
            return "member/join";
        }
        try{
            memberService.saveMember(memberFormDto, passwordEncoder);
        }catch(IllegalStateException e1){
            bindingResult.rejectValue("memberUserId", "error.memberFormDto", e1.getMessage());
            return "member/join";
        }catch(IllegalArgumentException e2){
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






}
