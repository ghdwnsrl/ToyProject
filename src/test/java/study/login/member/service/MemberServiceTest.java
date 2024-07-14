package study.login.member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.login.common.exception.DuplicateUserException;
import study.login.common.exception.UserNotFoundException;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;
import study.login.mock.FakeMemberRepository;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest {

    private MemberService memberService;

    @BeforeEach
    void init() {
        memberService = MemberServiceImpl.builder()
                .memberRepository(new FakeMemberRepository())
                .build();
    }

    @Test
    void 신규회원은_기존_회원의_아이디와_중복될_수_없다() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        MemberCreate memberCreate2 = MemberCreate.builder()
                .userId("hong")
                .password("pw112")
                .nickname("hongs2")
                .build();

        Member member = memberService.join(memberCreate);
        //when
        // then
        Assertions.assertThatThrownBy(()->memberService.join(memberCreate2))
                .isInstanceOf(DuplicateUserException.class);
    }

    @Test
    void join은_MemberCreate로_회원가입을_할_수_있다() {
        //given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();
        //when
        Member member = memberService.join(memberCreate);
        //then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getUserId()).isEqualTo("hong");
        assertThat(member.getPassword()).isEqualTo("pw111");
        assertThat(member.getNickname()).isEqualTo("hongs");

    }

    @Test
    void findByUserId는_userId로_member를_찾을_수_있다() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();
        memberService.join(memberCreate);
        // when
        Member result = memberService.findByUserId("hong");
        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo("hong");
        assertThat(result.getPassword()).isEqualTo("pw111");
        assertThat(result.getNickname()).isEqualTo("hongs");
    }

    @Test
    void findByUserId는_없는_userId일_경우_예외를_던진다() {
        // given
        // when
        // then
        assertThatThrownBy(()-> memberService.findByUserId("noInDB"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void Member를_저장_할_수_있다() throws Exception {
        //given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        //when
        member = memberService.save(member);

        //then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getUserId()).isEqualTo("hong");
        assertThat(member.getPassword()).isEqualTo("pw111");
        assertThat(member.getNickname()).isEqualTo("hongs");
    }
}
