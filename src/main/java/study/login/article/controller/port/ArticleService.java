package study.login.article.controller.port;

import org.springframework.data.domain.Page;
import study.login.article.domain.Article;
import study.login.article.domain.ArticleDetailDto;
import study.login.article.domain.ArticleDto;
import study.login.article.domain.ArticleWriteForm;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;

import java.util.Optional;

public interface ArticleService {
    Page<ArticleDto> findLists(int page);

    void write(ArticleWriteForm articleWriteForm, Member member);

    void write(Article article);

    Optional<Article> findByArticleId(Long articleId);

    void deleteArticle(Long articleId);

    void update(ArticleDetailDto articleDetailDto);

    void increaseArticleViews(Article article);

    boolean isOwner(Article article, MemberCreate memberCreate);
}
