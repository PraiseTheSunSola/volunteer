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

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //DB에 회원가입 폼 저장하기
    public void saveMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = memberFormDto.createEntity(passwordEncoder);
        //아이디 중복여부 확인 (이메일 중복여부도 포함시키려면 vaildmemberUserIdandEmail(member)로 수정)
        vaildmemberUserId(member);
        memberRepository.save(member);
    }


    //아이디(+이메일) 중복검증 로직
    private void vaildmemberUserId(Member member) {
        Member find = memberRepository.findByMemberUserId(member.getMemberUserId());
        if(find != null){
            throw new IllegalStateException("이미 가입된 아이디 입니다.");
        }
        //아이디 외에도 이메일 중복여부 확인하려면 해당 메서드명을 위에서 지정한 것처럼 vaildmemberUserIdandEmail로 바꾸고
        //MemberRepository에서도 적용시켜야 합니다.
        //더불어, 해당 메서드 명으로 바꿀 경우 아래의 if문에 설정한 주석을 해제해 주세요.
        /*
        if(find != null){
            throw new IllegalArgumentException("이미 가입된 이메일 입니다.);
        */
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //로그인 시 입력한 아이디로 계정조회
        Member member = memberRepository.findByMemberUserId(username);
        if(member == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + username);
        }
        //입력한 비밀번호와 조회한 계정의 비밀번호 비교를 하기 위한 반환
        return User.builder()
                .username(member.getMemberUserId())
                .password(member.getMemberPassword())
                .roles(member.getRole().toString()).build();

    }


}
