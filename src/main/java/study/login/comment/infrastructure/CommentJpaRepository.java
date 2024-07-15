package study.login.comment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long > {

    List<CommentEntity> findByArticleEntityId(Long articleId);

    @Modifying
    @Query(value = "delete from CommentEntity c where c.articleEntity.id = :id")
    void deleteByArticleEntityId(@Param(value = "id") Long articleId);
}
