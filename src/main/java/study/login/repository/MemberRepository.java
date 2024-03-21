package study.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.login.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByUserId(String userId);
}
