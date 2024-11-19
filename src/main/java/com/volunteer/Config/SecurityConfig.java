package com.volunteer.Config;

import com.volunteer.Service.MemberService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;

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

        // 권한에 따른 접근 설정
        http.authorizeRequests()
                .mvcMatchers("/", "/member/**", "/image/**").permitAll()  // 모든 사용자가 접근 가능
                .mvcMatchers("/css/**", "/js/**", "/image/**", "/Editor/**").permitAll()  // 정적 리소스 접근 허용
                .mvcMatchers("/admin/**").hasRole("ADMIN")  // 'ADMIN'만 사용하고, 내부적으로 ROLE_가 자동으로 붙음
                .mvcMatchers("/volunteer/**", "/mypage/**").hasAnyRole("USER", "ADMIN")  // 'USER', 'ADMIN' 권한 모두 접근 가능
                .mvcMatchers("/fetch-data/**").permitAll()  // 외부 API나 데이터 요청에 대한 접근 허용
                .anyRequest().authenticated();  // 나머지 요청은 인증된 사용자만 접근 가능

        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        return http.build();
    }
}
