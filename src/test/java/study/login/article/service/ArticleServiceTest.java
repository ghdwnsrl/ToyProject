package study.login.article.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import study.login.article.controller.port.ArticleService;
import study.login.article.domain.Article;
import study.login.article.domain.ArticleDetailDto;
import study.login.article.domain.ArticleDto;
import study.login.article.domain.ArticleWriteForm;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.LoginMember;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;
import study.login.member.service.MemberServiceImpl;
import study.login.mock.FakeArticleRepository;
import study.login.mock.FakeMemberRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class ArticleServiceTest {

    private ArticleService articleService;
    private MemberService memberService;

    @BeforeEach
    void init() {

        memberService = MemberServiceImpl.builder()
                .memberRepository(new FakeMemberRepository())
                .build();

        articleService = ArticleServiceImpl.builder()
                .memberService(memberService)
                .articleRepository(new FakeArticleRepository())
                .build();

    }

    private void createInitialData(Member member) {
        for (int i = 0; i < 30; i++) {
            articleService.write(
                    ArticleWriteForm.builder()
                            .title("테스트 용 title " + i)
                            .contents("테스트 용 contents " + i)
                            .build(), member
            );
        }
    }

    @Test
    void findLists로_Page를_받을_수_있다() throws Exception {

        // given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        createInitialData(member);

        // when
        Page<ArticleDto> lists = articleService.findLists(0);

        // then
        assertThat(lists.getNumberOfElements()).isEqualTo(10);
        assertThat(lists.getTotalElements()).isEqualTo(30);
        assertThat(lists.getTotalPages()).isEqualTo(3);
        assertThat(lists.getNumber()).isEqualTo(0);
    }


    @Test
    public void 다른_LoginMember로_read를_하면_isOwner가_false를_반환한다() {

        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        Member member = memberService.join(memberCreate);
        Article article = articleService.write(articleWriteForm, member);

        LoginMember loginMember = LoginMember.builder()
                .userId("other_user")
                .nickname("other_user")
                .build();

        // when
        Map<String, Object> readResult = articleService.read(article.getId(), loginMember, false);

        article = (Article) readResult.get("article");
        Boolean isOwner = (Boolean) readResult.get("isOwner");

        // then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo("테스트 용 title");
        assertThat(article.getContents()).isEqualTo("테스트 용 contents");
        assertThat(article.getMember().getId()).isNotNull();
        assertThat(article.getMember().getUserId()).isEqualTo("hong");
        assertThat(article.getMember().getPassword()).isEqualTo("pw111");
        assertThat(article.getMember().getNickname()).isEqualTo("hongs");
        assertThat(article.getViews()).isEqualTo(1);

        assertThat(isOwner).isFalse();

    }

    @Test
    public void read로_Article과_isOwner를_반환할_수_있고_fromComment가_false면_views가_증가한다() {

        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        Member member = memberService.join(memberCreate);
        Article article = articleService.write(articleWriteForm, member);

        LoginMember loginMember = LoginMember.builder()
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .build();

        // when
        Map<String, Object> readResult = articleService.read(article.getId(), loginMember, false);

        article = (Article) readResult.get("article");
        Boolean isOwner = (Boolean) readResult.get("isOwner");

        // then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo("테스트 용 title");
        assertThat(article.getContents()).isEqualTo("테스트 용 contents");
        assertThat(article.getMember().getId()).isNotNull();
        assertThat(article.getMember().getUserId()).isEqualTo("hong");
        assertThat(article.getMember().getPassword()).isEqualTo("pw111");
        assertThat(article.getMember().getNickname()).isEqualTo("hongs");
        assertThat(article.getViews()).isEqualTo(1);

        assertThat(isOwner).isTrue();

    }

    @Test
    public void read로_Article과_isOwner를_반환할_수_있고_fromComment가_true면_views가_증가한다() {

        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        Member member = memberService.join(memberCreate);
        Article article = articleService.write(articleWriteForm, member);

        LoginMember loginMember = LoginMember.builder()
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .build();

        // when
        Map<String, Object> readResult = articleService.read(article.getId(), loginMember, false);

        article = (Article) readResult.get("article");
        Boolean isOwner = (Boolean) readResult.get("isOwner");

        // then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo("테스트 용 title");
        assertThat(article.getContents()).isEqualTo("테스트 용 contents");
        assertThat(article.getMember().getId()).isNotNull();
        assertThat(article.getMember().getUserId()).isEqualTo("hong");
        assertThat(article.getMember().getPassword()).isEqualTo("pw111");
        assertThat(article.getMember().getNickname()).isEqualTo("hongs");
        assertThat(article.getViews()).isEqualTo(1);

        assertThat(isOwner).isTrue();

    }

    @Test
    public void read로_Article과_isOwner를_반환할_수_있고_doIncrease가_false면_views는_증가하지_않는다() {

        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        Member member = memberService.join(memberCreate);
        Article article = articleService.write(articleWriteForm, member);

        LoginMember loginMember = LoginMember.builder()
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .build();

        // when
        Map<String, Object> readResult = articleService.read(article.getId(), loginMember, true);

        article = (Article) readResult.get("article");
        Boolean isOwner = (Boolean) readResult.get("isOwner");

        // then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo("테스트 용 title");
        assertThat(article.getContents()).isEqualTo("테스트 용 contents");
        assertThat(article.getMember().getId()).isNotNull();
        assertThat(article.getMember().getUserId()).isEqualTo("hong");
        assertThat(article.getMember().getPassword()).isEqualTo("pw111");
        assertThat(article.getMember().getNickname()).isEqualTo("hongs");
        assertThat(article.getViews()).isEqualTo(0);

        assertThat(isOwner).isTrue();

    }


    @Test
    public void wrtie로_ArticleWriteForm과_Member를_사용하여_Article을_저장할_수_있다() throws Exception {
        //given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        member = memberService.save(member);

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        //when
        Article article = articleService.write(articleWriteForm, member);

        //then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo("테스트 용 title");
        assertThat(article.getContents()).isEqualTo("테스트 용 contents");
        assertThat(article.getMember().getId()).isNotNull();
        assertThat(article.getMember().getUserId()).isEqualTo("hong");
        assertThat(article.getMember().getPassword()).isEqualTo("pw111");
        assertThat(article.getMember().getNickname()).isEqualTo("hongs");
        assertThat(article.getViews()).isEqualTo(0);
    }

    @Test
    void findById로_Article을_찾을_수_있다() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        Member member = memberService.join(memberCreate);
        Article article = articleService.write(articleWriteForm, member);
        // when
        Optional<Article> byId = articleService.findById(article.getId());

        // then
        assertThat(byId.isPresent()).isTrue();
    }

    @Test
    void update로_Article의_title과_contents를_수정할_수_있다() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        Member member = memberService.join(memberCreate);
        Article article = articleService.write(articleWriteForm, member);

        ArticleDetailDto updateArticleDto = ArticleDetailDto.builder()
                .id(article.getId())
                .title("업데이트된 title")
                .contents("업데이트된 contents")
                .build();

        // when
        article = articleService.update(updateArticleDto);

        // then

        assertThat(article.getTitle()).isEqualTo("업데이트된 title");
        assertThat(article.getContents()).isEqualTo("업데이트된 contents");

    }

    @Test
    public void delete메서드와_articleId로_Article을_삭제할_수_있다() throws Exception {
        //given
        MemberCreate memberCreate = MemberCreate.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("테스트 용 title")
                .contents("테스트 용 contents")
                .build();

        Member member = memberService.join(memberCreate);
        Article article = articleService.write(articleWriteForm, member);

        //when
        articleService.deleteById(article.getId());

        //then
        Optional<Article> byId = articleService.findById(article.getId());
        assertThat(byId).isNotPresent();
    }
}
