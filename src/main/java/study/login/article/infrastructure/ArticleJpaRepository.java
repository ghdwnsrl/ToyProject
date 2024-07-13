package study.login.article.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import study.login.article.domain.ArticleEntity;

public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, Long> {
    Page<ArticleEntity> findAll(Pageable pageable);
}
