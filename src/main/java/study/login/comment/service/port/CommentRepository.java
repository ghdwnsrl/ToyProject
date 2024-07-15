package study.login.comment.service.port;

import study.login.comment.domain.Comment;

import java.util.List;

public interface CommentRepository {
    void deleteByArticleId(Long articleId);

    List<Comment> findByArticleId(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}
