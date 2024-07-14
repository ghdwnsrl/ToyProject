package study.login.member.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.common.exception.DuplicateUserException;
import study.login.common.exception.UserNotFoundException;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;
import study.login.member.service.port.MemberRepository;

@RequiredArgsConstructor
@Builder
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(MemberCreate memberCreate) {

        if (memberRepository.findByUserId(memberCreate.getUserId()).isPresent())
            throw new DuplicateUserException("중복된 회원입니다.");

        Member member = Member.builder()
                .userId(memberCreate.getUserId())
                .password(memberCreate.getPassword())
                .nickname(memberCreate.getNickname())
                .build();

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

}
