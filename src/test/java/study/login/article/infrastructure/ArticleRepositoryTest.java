package study.login.article.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import study.login.article.domain.Article;
import study.login.article.service.port.ArticleRepository;
import study.login.member.domain.Member;
import study.login.member.infrastructure.MemberJpaRepository;
import study.login.member.infrastructure.MemberRepositoryImpl;
import study.login.member.service.port.MemberRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@SqlGroup({
        @Sql("/sql/member-repository-test-data.sql"),
        @Sql("/sql/article-repository-test-data.sql"),
})
public class ArticleRepositoryTest {

    @Autowired
    private ArticleJpaRepository articleJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private ArticleRepository articleRepository;

    private MemberRepository memberRepository;


    @BeforeEach
    void init() {
        articleRepository = ArticleRepositoryImpl.builder()
                .articleJpaRepository(articleJpaRepository)
                .build();

        memberRepository = MemberRepositoryImpl.builder()
                .memberJpaRepository(memberJpaRepository)
                .build();
    }

    @Test
    void Article을_저장할_수_있다() throws Exception {

        //given
        Member member = Member.builder()
                .userId("id_test")
                .password("pw_test")
                .nickname("test_nickname")
                .build();

        member = memberRepository.save(member);

        Article article = Article.builder()
                .title("title")
                .contents("contents")
                .views(0)
                .member(member)
                .build();

        //when
        article = articleRepository.save(article);

        //then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo("title");
        assertThat(article.getContents()).isEqualTo("contents");
        assertThat(article.getViews()).isEqualTo(0);
        assertThat(article.getMember()).isNotNull();
        assertThat(article.getMember().getUserId()).isEqualTo("id_test");
        assertThat(article.getMember().getPassword()).isEqualTo("pw_test");
        assertThat(article.getMember().getNickname()).isEqualTo("test_nickname");

    }

    @Test
    void deleteById로_Article을_지울_수_있다() throws Exception {

        //given
        Optional<Article> before = articleRepository.findById(1L);

        //when
        articleRepository.deleteById(1L);

        //then
        Optional<Article> after = articleRepository.findById(1L);

        Assertions.assertThat(before).isPresent();
        Assertions.assertThat(after).isNotPresent();
    }

    @Test
    void findAll로_Page타입을_반환_받을_수_있다() throws Exception {

        //given
        //when
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(0, 5, Sort.by(sorts));

        Page<Article> result = articleRepository.findAll(pageable);

        //then
        assertThat(result.getNumberOfElements()).isEqualTo(5);
        assertThat(result.getTotalElements()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getNumber()).isEqualTo(0);

    }

}
