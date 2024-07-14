package study.login.member.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import study.login.member.domain.Member;
import study.login.member.service.port.MemberRepository;

import java.util.Optional;

@DataJpaTest
@Sql("/sql/member-repository-test-data.sql")
public class MemberRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private MemberRepository memberRepository;

    @BeforeEach
    void init() {
        memberRepository = MemberRepositoryImpl.builder()
                .memberJpaRepository(memberJpaRepository)
                .build();
    }

    @Test
    void Member를_저장할_수_있다() {

        // given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        // when
        // then
        member = memberRepository.save(member);

        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getUserId()).isEqualTo("hong");
        Assertions.assertThat(member.getPassword()).isEqualTo("pw111");
        Assertions.assertThat(member.getNickname()).isEqualTo("hongs");
    }

    @Test
    void findByUserId로_Member를_찾을_수_있다() {

        // given
        // when
        Optional<Member> result = memberRepository.findByUserId("hong");
        // then
        Assertions.assertThat(result).isPresent();

    }

    @Test
    public void findById로_Member를_찾을_수_있다() throws Exception {
        //given
        //when
        Optional<Member> result = memberRepository.findById(1L);
        //then
        Assertions.assertThat(result).isPresent();
    }

}
