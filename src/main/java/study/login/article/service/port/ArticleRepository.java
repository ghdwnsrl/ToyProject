package study.login.article.service.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.login.article.domain.Article;

import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    void deleteById(Long id);

    Optional<Article> findById(Long id);

    Page<Article> findAll(Pageable pageable);
}
