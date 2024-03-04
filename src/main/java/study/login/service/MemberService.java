package study.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.domain.Member;
import study.login.repository.MemberRepository;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(Member member) {

        if (this.findMember(member.getUserId()) == null ) {
            return memberRepository.save(member);
        }

        return null;
    }

    public Member findMember(String userId) {
        return memberRepository.findByUserId(userId);
    }


    public void saveUser(Member member) {
        memberRepository.save(member);
    }
}
