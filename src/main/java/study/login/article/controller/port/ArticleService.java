package study.login.article.controller.port;

import org.springframework.data.domain.Page;
import study.login.article.domain.Article;
import study.login.article.domain.ArticleDetailDto;
import study.login.article.domain.ArticleDto;
import study.login.article.domain.ArticleWriteForm;
import study.login.member.domain.LoginMember;
import study.login.member.domain.Member;

import java.util.Map;
import java.util.Optional;

public interface ArticleService {
    Page<ArticleDto> findLists(int page);

    Article write(ArticleWriteForm articleWriteForm, Member member);

    Optional<Article> findById(Long articleId);

    void deleteById(Long articleId);

    Article update(ArticleDetailDto articleDetailDto);

    Map<String, Object> read(Long articleId, LoginMember loginMember, Boolean fromComment);
}
