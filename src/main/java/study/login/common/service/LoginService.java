package study.login.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.login.common.exception.InvalidLoginException;
import study.login.common.exception.UserNotFoundException;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;
import study.login.member.service.port.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public MemberCreate login(String userId, String password) {

        Member member = memberRepository
                .findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        if (loginCheck(password, member)) {
            return MemberCreate.builder()
                    .userId(member.getUserId())
                    .password(member.getPassword())
                    .nickname(member.getNickname())
                    .build();
        } else{
            throw new InvalidLoginException(" ID / PASSWORD 불일치");
        }
    }

    private static boolean loginCheck(String password, Member byUserId) {
        return byUserId.getPassword().equals(password);
    }
}