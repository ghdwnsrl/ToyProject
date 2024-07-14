package study.login.common.controller.port;

import study.login.member.domain.LoginMember;

public interface LoginService {
    LoginMember login(String userId, String password);
}
