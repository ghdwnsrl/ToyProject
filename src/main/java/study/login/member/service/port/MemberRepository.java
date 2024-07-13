package study.login.member.service.port;

import study.login.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findById(Long userId);
}
