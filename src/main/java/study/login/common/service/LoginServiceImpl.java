package study.login.common.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.login.common.controller.port.LoginService;
import study.login.common.exception.InvalidLoginException;
import study.login.common.exception.UserNotFoundException;
import study.login.member.domain.LoginMember;
import study.login.member.domain.Member;
import study.login.member.service.port.MemberRepository;



@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;

    public LoginMember login(String userId, String password) {

        Member member = memberRepository
                .findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        if (loginCheck(password, member)) {
            return LoginMember.builder()
                    .userId(member.getUserId())
                    .nickname(member.getNickname())
                    .build();
        } else{
            throw new InvalidLoginException(" ID / PASSWORD 불일치");
        }
    }

    private boolean loginCheck(String password, Member byUserId) {
        return byUserId.getPassword().equals(password);
    }
}