package study.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.login.domain.Member;
import study.login.dto.MemberDto;
import study.login.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public MemberDto login(String userId, String password) {


        log.info("userId={}, password={}", userId, password);
        Member byUserId = memberRepository.findByUserId(userId);
        log.info("findByUserId Result = {}", byUserId);
        if (byUserId == null) {
            return null;
        }

        if (byUserId.getPassword().equals(password))
            return new MemberDto(byUserId.getId(), byUserId.getUserId(), byUserId.getPassword(), byUserId.getNickname());

        return null;
    }
}