package com.volunteer.Control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping( "/customerServiceCenter" )
public class CustomerServiceCenterControl {

    // 고객센터 메인페이지
    @GetMapping("/customerServiceCenterIndex")
    public String customerServiceCenterMain() {return "customerServiceCenter/customerServiceCenterIndex";}
}
