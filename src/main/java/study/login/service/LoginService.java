package study.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.login.domain.Member;
import study.login.dto.MemberDto;
import study.login.exception.DuplicateUserException;
import study.login.exception.InvalidLoginException;
import study.login.exception.UserNotFoundException;
import study.login.repository.MemberRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public MemberDto login(String userId, String password) {

        Member member = memberRepository
                .findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        if (loginCheck(password, member)) {
            return new MemberDto(member);
        } else{
            throw new InvalidLoginException(" ID / PASSWORD 불일치");
        }
    }

    private static boolean loginCheck(String password, Member byUserId) {
        return byUserId.getPassword().equals(password);
    }
}