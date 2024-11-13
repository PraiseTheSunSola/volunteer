package com.volunteer.Control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    //통계 상세
    //주소 statistic/daily/1 뭐 이런 식으로 해당하는 항목을 볼 수 있게 분리 (가능한가?)
    //불가능할 경우 각각 따로 만들어야지... 페이징은 가능하겠죠
    @GetMapping("/statistic")
    public String statistic(Model model) {
        return "admin/dashboardDetail";
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
    @PostMapping("/content/write")
    public String contentSave(Model model) {
        return "redirect:/admin/content";
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
    @PostMapping("/user/mail")
    public String userMailSave(Model model) {
        return "redirect:/admin/user";
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
    @PostMapping("/report/process")
    public String reportProcessSave(Model model) {
        return "redirect:/admin/report/process";
    }
}
