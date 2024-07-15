package study.login.comment.service.port;

import study.login.comment.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long id);

    void deleteByArticleId(Long articleId);

    List<Comment> findByArticleId(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}
