package com.volunteer.Repository;

import com.volunteer.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberUserId(String username);
    Optional<Member> findByMemberEmail(String email);
    Optional<Member> findByMemberUserIdAndMemberEmail(String userId, String email);
}
