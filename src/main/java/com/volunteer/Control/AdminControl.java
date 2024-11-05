package com.volunteer.Control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControl {

    //관리자 메뉴 메인: 대시보드
    @GetMapping("")
    public String adminMain(Model model) {
        return "admin/dashboard";
    }
    
    //대시보드 링크로도 접속 가능 -> 메인으로 리다이렉트
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "redirect:/admin";
    }

    //콘텐츠
    @GetMapping("/content")
    public String content(Model model) {
        return "admin/contentAdmin";
    }
    //콘텐츠 작성
    @GetMapping("/content/write")
    public String contentWrite(Model model) {
        return "admin/contentWrite";
    }

    //멤버
    @GetMapping("/user")
    public String user(Model model) {
        return "admin/userAdmin";
    }
    //단체 메일
    @GetMapping("/user/mail")
    public String userMail(Model model) {
        return "admin/userGroupMail";
    }

    //신고
    @GetMapping("/report")
    public String report(Model model) {
        return "admin/reportAdmin";
    }
    //신고 처리
    @GetMapping("/report/process")
    public String reportProcess(Model model) {
        return "admin/reportProcess";
    }
}
