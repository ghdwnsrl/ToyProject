package study.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.login.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
