package study.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.login.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByUserId(String userId);
}
