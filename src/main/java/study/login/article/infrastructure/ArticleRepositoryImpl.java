package study.login.article.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import study.login.article.domain.Article;
import study.login.article.domain.ArticleEntity;
import study.login.article.service.port.ArticleRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public Article save(Article article) {
        return articleJpaRepository.save(ArticleEntity.from(article)).toModel();
    }

    @Override
    public void deleteById(Long id) {
        articleJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleJpaRepository.findById(id).map(ArticleEntity::toModel);
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        Page<Article> map = articleJpaRepository.findAll(pageable).map(ArticleEntity::toModel);
        return map;
    }
}
