package study.login.mock;

import org.springframework.data.domain.*;
import study.login.article.domain.Article;
import study.login.article.service.port.ArticleRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeArticleRepository implements ArticleRepository {

    private final List<Article> data = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Article save(Article article) {
        if (article.getId() == null || article.getId() == 0) {
            Article newArticle = Article.builder()
                    .id(sequence.incrementAndGet())
                    .title(article.getTitle())
                    .contents(article.getContents())
                    .member(article.getMember())
                    .views(article.getViews())
                    .build();

            data.add(newArticle);
            return newArticle;
        } else {
            data.removeIf(m -> Objects.equals(m.getId(), article.getId()));
            data.add(article);
            return article;
        }
    }

    @Override
    public void deleteById(Long id) {
        data.removeIf(article -> article.getId().equals(id));
    }

    @Override
    public Optional<Article> findById(Long id) {
        return data.stream().filter(article -> Objects.equals(article.getId(), id)).findAny();
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), data.size());

        List<Article> articles = this.data.subList(start, end);

        return new PageImpl<>(articles, pageable, data.size());
    }
}