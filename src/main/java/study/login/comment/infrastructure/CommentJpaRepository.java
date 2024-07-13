package study.login.comment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long > {

    List<CommentEntity> findByArticleEntityId(Long articleId);

    void deleteByArticleEntityId(Long articleId);
}
