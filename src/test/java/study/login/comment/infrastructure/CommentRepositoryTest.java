package study.login.comment.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import study.login.article.controller.port.ArticleService;
import study.login.article.domain.Article;
import study.login.article.infrastructure.ArticleEntity;
import study.login.article.infrastructure.ArticleJpaRepository;
import study.login.article.service.ArticleServiceImpl;
import study.login.comment.domain.Comment;
import study.login.comment.service.port.CommentRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@SqlGroup({
        @Sql("/sql/member-repository-test-data.sql"),
        @Sql("/sql/article-repository-test-data.sql"),
        @Sql("/sql/comment-repository-test-data.sql")
})
public class CommentRepositoryTest {

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private ArticleJpaRepository articleJpaRepository;

    private CommentRepository commentRepository;

    @BeforeEach
    void init() {
        commentRepository = CommentRepositoryImpl.builder()
                .commentJpaRepository(commentJpaRepository)
                .build();


    }

    @Test
    void article_id로_댓글을_지울_수_있다() {

        // given
        List<Comment> before = commentRepository.findByArticleId(1L);

        // when
        commentRepository.deleteByArticleId(1L);

        // then
        Assertions.assertThat(commentRepository.findByArticleId(1L)).isEmpty();
        Assertions.assertThat(before.size()).isEqualTo(2);

    }

    @Test
    void 사용자는_댓글을_달_수_있다() {

        // given

        Article article = articleJpaRepository.findById(1L).map(ArticleEntity::toModel).get();

        Comment comment = Comment.builder()
                .article(article)
                .contents("hello")
                .nickname("junn")
                .build();
        // when
        comment = commentRepository.save(comment);

        // then
        Assertions.assertThat(comment.getId()).isNotNull();

    }

    @Test
    void deleteById를_통해_댓글을_지울_수_있다() {

        // given
        // when
        commentRepository.deleteById(1L);

        // then
        Optional<CommentEntity> comment = commentJpaRepository.findById(1L);

        Assertions.assertThat(comment).isNotPresent();

    }

    @Test
    void 글에_작성된_댓글들을_볼_수_있다() {

        // given
        // when
        List<Comment> result = commentRepository.findByArticleId(1L);

        // then
        Assertions.assertThat(result.size()).isEqualTo(2);

    }

}
