package study.login.member.controller.port;

import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;

public interface MemberService {
    Member join(MemberCreate memberCreate);

    Member findByUserId(String userId);

    Member save(Member member);
}
