package study.login.comment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.login.article.controller.port.ArticleService;
import study.login.article.domain.Article;
import study.login.article.domain.ArticleWriteForm;
import study.login.article.service.ArticleServiceImpl;
import study.login.comment.controller.port.CommentService;
import study.login.comment.domain.Comment;
import study.login.comment.domain.CommentDto;
import study.login.comment.domain.CommentListDto;
import study.login.common.exception.PermissionDeniedException;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.Member;
import study.login.member.service.MemberServiceImpl;
import study.login.mock.FakeArticleRepository;
import study.login.mock.FakeCommentRepository;
import study.login.mock.FakeMemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class CommentServiceTest {

    private CommentService commentService;
    private ArticleService articleService;
    private MemberService memberService;
    private FakeCommentRepository commentRepository;

    @BeforeEach
    void init() {

        memberService = MemberServiceImpl.builder()
                .memberRepository(new FakeMemberRepository())
                .build();

        articleService = ArticleServiceImpl.builder()
                .memberService(memberService)
                .articleRepository(new FakeArticleRepository())
                .build();

        commentRepository = new FakeCommentRepository();

        commentService = CommentServiceImpl.builder()
                .articleService(articleService)
                .commentRepository(commentRepository)
                .build();

    }

    @Test
    void 댓글_작성자가_아닌_사람이_삭제하면_예외가_발생한다() {
        // given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("title")
                .contents("contents")
                .build();

        Article article = articleService.write(articleWriteForm, member);

        CommentDto commentDto = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 1")
                .build();

        Comment comment = commentService.write(commentDto);

        // when
        // then
        assertThatThrownBy(()->commentService.deleteById(comment.getId(), "kim"))
                .isInstanceOf(PermissionDeniedException.class);

    }


    @Test
    void CommentDto로_댓글을_달_수_있다() {
        // given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("title")
                .contents("contents")
                .build();

        Article article = articleService.write(articleWriteForm, member);

        CommentDto commentDto = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 1")
                .build();

        // when
        Comment comment = commentService.write(commentDto);

        // then
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getContents()).isEqualTo("댓글 1");
        assertThat(comment.getNickname()).isEqualTo(member.getNickname());
        assertThat(comment.getArticle().getId()).isEqualTo(article.getId());
        assertThat(comment.getArticle().getTitle()).isEqualTo(article.getTitle());
        assertThat(comment.getArticle().getMember()).isNotNull();
        assertThat(comment.getArticle().getViews()).isEqualTo(article.getViews());
    }

    @Test
    void 특정_게시글의_댓글_리스트를_찾을_수_있다() {

        // given

        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("title")
                .contents("contents")
                .build();

        Article article = articleService.write(articleWriteForm, member);

        CommentDto commentDto = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 1")
                .build();

        CommentDto commentDto2 = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 2")
                .build();

        CommentDto commentDto3 = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 3")
                .build();

        commentService.write(commentDto);
        commentService.write(commentDto2);
        commentService.write(commentDto3);

        // when
        List<CommentListDto> result = commentService.findList(article.getId());

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getContents()).isEqualTo("댓글 1");
        assertThat(result.get(1).getContents()).isEqualTo("댓글 2");
        assertThat(result.get(2).getContents()).isEqualTo("댓글 3");

    }

    @Test
    void Comment의_id로_comment를_삭제할_수_있다() {
        // given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("title")
                .contents("contents")
                .build();

        Article article = articleService.write(articleWriteForm, member);

        CommentDto commentDto = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 1")
                .build();

        Comment comment = commentService.write(commentDto);

        // when
        Optional<Comment> before = commentRepository.findById(comment.getId());
        commentService.deleteById(comment.getId(), "hongs");
        Optional<Comment> after = commentRepository.findById(comment.getId());
        // then
        assertThat(before).isPresent();
        assertThat(after).isNotPresent();
    }

    @Test
    void Article의_id로_comment를_한번에_삭제할_수_있다() {
        // given
        Member member = Member.builder()
                .userId("hong")
                .password("pw111")
                .nickname("hongs")
                .build();

        ArticleWriteForm articleWriteForm = ArticleWriteForm.builder()
                .title("title")
                .contents("contents")
                .build();

        Article article = articleService.write(articleWriteForm, member);


        CommentDto commentDto = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 1")
                .build();

        CommentDto commentDto2 = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 2")
                .build();

        CommentDto commentDto3 = CommentDto.builder()
                .articleId(article.getId())
                .nickname(member.getNickname())
                .contents("댓글 3")
                .build();


        Comment comment = commentService.write(commentDto);
        Comment comment2 = commentService.write(commentDto2);
        Comment comment3 = commentService.write(commentDto3);
        // when
        commentService.deleteByArticleId(article.getId());
        List<CommentListDto> result = commentService.findList(article.getId());

        // then
        assertThat(result).isEmpty();
    }

}
