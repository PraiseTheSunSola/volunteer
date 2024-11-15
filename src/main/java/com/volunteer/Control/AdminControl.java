package com.volunteer.Control;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminControl {
    //관리자 메뉴 메인: 대시보드
    @GetMapping("")
    public String adminMain(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login";
        }

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
    @GetMapping(value={"/content", "/content/{page}"})
    public String content(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        return "admin/contentAdmin";
    }
    //콘텐츠 작성
    @GetMapping("/content/write")
    public String contentWrite() {
        return "admin/contentWrite";
    }
    @PostMapping("/content/write")
    public String contentSave(@RequestParam("title") String title,
                              @RequestParam("categorySelect") String category,
                              @RequestParam("termStartRecruit") LocalDateTime recruitStart,
                              @RequestParam("termUntilRecruit") LocalDateTime recruitEnd,
                              @RequestParam("goalCost") int goalCost,
                              @RequestParam("termStartActivity") LocalDateTime activityStart,
                              @RequestParam("termUntilActivity") LocalDateTime activityEnd,
                              @RequestParam("age") String age,
                              @RequestParam("group") String group,
                              @RequestParam("recruitCount") int recruitCount,
                              @RequestParam("agencyName") String agencyName,
                              @RequestParam("agencyTel") long agencyTel,
                              @RequestParam("agencyEmail") String agencyEmail,
                              @RequestParam("contentBody") String content,
                              RedirectAttributes redirectAttributes) {

        return "redirect:/admin/content";
    }

    //멤버
    @GetMapping(value={"/user", "/user/{page}"})
    public String user(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
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
    @GetMapping(value={"/report", "/report/{page}"})
    public String report(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        // 페이징을 위한 코드
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);
        // PageRequest.of(몇 번째 페이지, 한 페이지에 몇 개);
        // isPresent -> 값이 있는지

        return "admin/reportAdmin";
    }
    //신고 처리
    @GetMapping("/report/process")
    public String reportProcess(Model model) {
        return "admin/reportProcess";
    }
    @PostMapping("/report/process/")
    public String reportProcessSave(Model model) {
        return "redirect:/admin/report/process";
    }
}
