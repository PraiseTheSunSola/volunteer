package com.volunteer.Service;

import com.volunteer.DTO.FindMemberDto;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final EmailService emailService; // 이메일 발송 서비스
    private final Map<String, String> verificationCodes = new HashMap<>(); // 간단한 메모리 기반 저장소 (실제 프로젝트에서는 Redis 사용)

    public void saveMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = memberFormDto.createEntity(passwordEncoder);
        vaildMemberUserId(member);
        memberRepository.save(member);
    }

    private void vaildMemberUserId(Member member) {
        if (memberRepository.findByMemberUserId(member.getMemberUserId()).isPresent()) {
            throw new IllegalStateException("이미 가입된 아이디입니다.");
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
    // 아이디 찾기 인증 코드 발송 메서드
    public String sendVerificationCodeForUserId(FindMemberDto findMemberDto) {
        Member member = memberRepository.findByMemberEmail(findMemberDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다."));

        if (member.getMemberName().equals(findMemberDto.getName())) {
            String verificationCode = UUID.randomUUID().toString().substring(0, 6);
            verificationCodes.put(findMemberDto.getEmail(), verificationCode);

            emailService.sendEmail(findMemberDto.getEmail(), "아이디 찾기 인증 코드", "인증 코드: " + verificationCode);
            return verificationCode;
        } else {
            throw new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다.");
        }
    }

    // 비밀번호 찾기 인증 코드 발송 메서드
    public String sendVerificationCodeForPassword(FindMemberDto findMemberDto) {
        Member member = memberRepository.findByMemberUserIdAndMemberEmail(findMemberDto.getUserId(), findMemberDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다."));

        if (member.getMemberName().equals(findMemberDto.getName())) {
            String verificationCode = UUID.randomUUID().toString().substring(0, 6);
            verificationCodes.put(findMemberDto.getEmail(), verificationCode);

            emailService.sendEmail(findMemberDto.getEmail(), "비밀번호 찾기 인증 코드", "인증 코드: " + verificationCode);
            return verificationCode;
        } else {
            throw new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다.");
        }
    }


    public String resetPasswordWithVerificationCode(FindMemberDto findMemberDto, String verificationCode , PasswordEncoder passwordEncoder) {
        // 인증 코드 검증
        if (!verificationCodes.containsKey(findMemberDto.getEmail())
                || !verificationCodes.get(findMemberDto.getEmail()).equals(verificationCode)) {
            throw new IllegalArgumentException("유효하지 않은 인증 코드입니다.");
        }

        Member member = memberRepository.findByMemberUserIdAndMemberEmail(findMemberDto.getUserId(), findMemberDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다."));

        if (!member.getMemberName().equals(findMemberDto.getName())) {
            throw new IllegalArgumentException("입력한 정보와 일치하는 회원이 없습니다.");
        }

        // 임시 비밀번호 생성 및 저장
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        member.setMemberPassword(passwordEncoder.encode(tempPassword));
        memberRepository.save(member);

        // 이메일 발송
        emailService.sendEmail(findMemberDto.getEmail(), "임시 비밀번호 발급", "임시 비밀번호: " + tempPassword);

        // 인증 코드 삭제
        verificationCodes.remove(findMemberDto.getEmail());

        return tempPassword;
    }
}