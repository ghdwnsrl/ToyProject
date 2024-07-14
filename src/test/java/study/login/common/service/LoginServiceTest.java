package study.login.common.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.login.common.controller.port.LoginService;
import study.login.common.exception.InvalidLoginException;
import study.login.common.exception.UserNotFoundException;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.LoginMember;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;
import study.login.member.service.MemberServiceImpl;
import study.login.mock.FakeMemberRepository;

import static org.assertj.core.api.Assertions.*;

public class LoginServiceTest {

    private LoginService loginService;
    private MemberService memberService;

    @BeforeEach
    void init() {

        FakeMemberRepository memberRepository = new FakeMemberRepository();

        loginService = LoginServiceImpl.builder()
                .memberRepository(memberRepository)
                .build();

        memberService = MemberServiceImpl.builder()
                .memberRepository(memberRepository)
                .build();

    }

    @Test
    void userId와_password로_login을_할_수_있다() {

        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        Member member = memberService.join(memberCreate);

        // when
        LoginMember loginMember = loginService.login(member.getUserId(), member.getPassword());

        // then
        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getUserId()).isEqualTo(member.getUserId());
        assertThat(loginMember.getNickname()).isEqualTo(member.getNickname());

    }

    @Test
    void userId와_잘못된_password로_login을_시도하면_예외를_던진다() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        Member member = memberService.join(memberCreate);

        // when
        // then
        assertThatThrownBy(()-> loginService.login(member.getUserId(), "wrong_pw"))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    void 없는_userId로_login을_시도하면_예외를_던진다() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        Member member = memberService.join(memberCreate);

        // when
        // then
        assertThatThrownBy(()-> loginService.login("wrong_id", member.getPassword()))
                .isInstanceOf(UserNotFoundException.class);
    }
}
