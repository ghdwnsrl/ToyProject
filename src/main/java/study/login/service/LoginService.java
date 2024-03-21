package study.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.login.domain.Member;
import study.login.dto.MemberDto;
import study.login.repository.MemberRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public MemberDto login(String userId, String password) {

        Member byUserId = memberRepository
                .findByUserId(userId)
                .orElseThrow(NoSuchElementException::new);

        if (loginCheck(password, byUserId)) {
            return new MemberDto(byUserId);
        } else{
            throw new IllegalArgumentException(" ID / PASSWORD 불일치");
        }
    }

    private static boolean loginCheck(String password, Member byUserId) {
        return byUserId.getPassword().equals(password);
    }
}