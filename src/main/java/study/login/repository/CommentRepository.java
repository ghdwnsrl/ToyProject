package study.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.login.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long >, CommentRepositoryCustom {

    public List<Comment> findByArticleId(Long articleId);

    void deleteByArticleId(Long articleId);
}
