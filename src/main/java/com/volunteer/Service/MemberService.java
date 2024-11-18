package com.volunteer.Service;

import com.volunteer.DTO.MemberFormDto;
import com.volunteer.Entity.Member;
import com.volunteer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // 이메일 발송 서비스



    public void saveMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = memberFormDto.createEntity(passwordEncoder);
        vaildmemberUserId(member);
        memberRepository.save(member);
    }

    private void vaildmemberUserId(Member member) {
        if (memberRepository.findByMemberUserId(member.getMemberUserId()).isPresent()) {
            throw new IllegalStateException("이미 가입된 아이디 입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return User.builder()
                .username(member.getMemberUserId())
                .password(member.getMemberPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public String sendVerificationCodeForUserId(String name, String birthdate, String email) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedBirthdate = LocalDate.parse(birthdate, formatter);

        Member member = memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다."));

        if (member.getMemberNickname().equals(name) && member.getMemberBirthdate().equals(parsedBirthdate)) {
            String verificationCode = UUID.randomUUID().toString().substring(0, 6);
            emailService.sendEmail(email, "아이디 찾기 인증 코드", "인증 코드: " + verificationCode);
            return verificationCode;
        } else {
            throw new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다.");
        }
    }

    public String resetPassword(String userId, String name, String email) {
        Member member = memberRepository.findByMemberUserIdAndMemberEmail(userId, email)
                .orElseThrow(() -> new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다."));

        if (member.getMemberNickname().equals(name)) {
            String tempPassword = UUID.randomUUID().toString().substring(0, 8);
            member.setMemberPassword(passwordEncoder.encode(tempPassword));
            memberRepository.save(member);

            emailService.sendEmail(email, "임시 비밀번호 발급", "임시 비밀번호: " + tempPassword);
            return tempPassword;
        } else {
            throw new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다.");
        }
    }
}
