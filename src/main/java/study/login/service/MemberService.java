package study.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.domain.Member;
import study.login.repository.MemberRepository;

@Service @Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public Member findMember(String userId) {
        return memberRepository.findByUserId(userId);
    }

}
