package com.volunteer.Config;

import com.volunteer.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/member/login")
                .loginProcessingUrl("/member/signIn")
                .defaultSuccessUrl("/")
                .usernameParameter("userId")
                .passwordParameter("userPW")
                .failureUrl("/member/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");

        //인가,인증 ,  누구든 접근 허용주소 설정
        http.authorizeRequests()
                .mvcMatchers("/", "/member/**", "/image/**").permitAll()
                .mvcMatchers("/css/**", "/js/**", "/image/**").permitAll()
                .mvcMatchers("/admin/**", "/mypage/**").permitAll() //작동 확인용 임시
                .mvcMatchers("/fetch-data/**").permitAll() //봉사활동 API 데이터 서버 전송용입니다.
                .anyRequest().authenticated();

        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        return http.build();
    }
}