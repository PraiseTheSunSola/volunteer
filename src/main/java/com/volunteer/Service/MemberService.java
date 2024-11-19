package com.volunteer.Service;

import com.volunteer.Constant.Role;
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

    // 회원 저장
    public void saveMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = memberFormDto.createEntity(passwordEncoder);
        vaildMemberUserId(member);

        // 권한 설정
        if (memberFormDto.isAdmin()) { // 관리자로 가입 여부 확인
            member.setRole(Role.ADMIN); // Role을 ADMIN으로 설정
        } else {
            member.setRole(Role.USER); // 기본 역할은 USER
        }

        memberRepository.save(member);
    }

    // 아이디 중복 확인
    private void vaildMemberUserId(Member member) {
        if (memberRepository.findByMemberUserId(member.getMemberUserId()).isPresent()) {
            throw new IllegalStateException("이미 가입된 아이디입니다.");
        }
    }

    // UserDetailsService 구현: 로그인 시 호출되는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        // Role에 ROLE_ 접두사 자동 추가되므로, 이를 제거하여 권한 설정
        return User.builder()
                .username(member.getMemberUserId())
                .password(member.getMemberPassword())
                .roles(member.getRole().name()) // ROLE_ 접두사를 자동으로 처리
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

    // 인증 코드로 비밀번호 재설정
    public String resetPasswordWithVerificationCode(FindMemberDto findMemberDto, String verificationCode, PasswordEncoder passwordEncoder) {
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

        // 인증 코드 제거
        verificationCodes.remove(findMemberDto.getEmail());

        return tempPassword;
    }
}
