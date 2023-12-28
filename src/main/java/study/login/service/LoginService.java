package study.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.login.domain.Member;
import study.login.dto.MemberDto;
import study.login.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public MemberDto login(String userId, String password) {
        Member byUserId = memberRepository.findByUserId(userId);

        if (byUserId == null) {
            return null;
        }

        if (byUserId.getPassword().equals(password))
            return new MemberDto(byUserId.getUserId() , byUserId.getPassword(), byUserId.getNickname());

        return null;
    }
}