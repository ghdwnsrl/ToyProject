package study.login.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.article.controller.port.ArticleService;
import study.login.article.domain.Article;
import study.login.article.domain.ArticleDetailDto;
import study.login.article.domain.ArticleDto;
import study.login.article.domain.ArticleWriteForm;
import study.login.article.service.port.ArticleRepository;
import study.login.common.exception.UserNotFoundException;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Page<ArticleDto> findLists(int page) {

        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        log.info(" here .. ");

        return articleRepository.findAll(pageable).map(m -> new ArticleDto(
                m.getId(),
                m.getTitle(),
                m.getMember().getNickname(),
                m.getViews()
        ));
    }

    @Transactional
    public void write(ArticleWriteForm articleWriteForm , Member member) {

        Article article = Article.builder()
                .title(articleWriteForm.getTitle())
                .contents(articleWriteForm.getContents())
                .member(member)
                .views(0)
                .build();

        articleRepository.save(article);
    }

    @Transactional
    public void write(Article article){
        articleRepository.save(article);
    }


    @Transactional(readOnly = true)
    public Optional<Article> findByArticleId(Long articleId) {
        return articleRepository.findById(articleId);
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional
    public void update(ArticleDetailDto articleDetailDto) {

        Article article = articleRepository.findById(articleDetailDto.getId()).orElseThrow(UserNotFoundException::new);

        article.update(
                articleDetailDto.getTitle(),
                articleDetailDto.getContents()
        );

        articleRepository.save(article);
    }

    @Transactional
    public void increaseArticleViews(Article article) {
        article.increaseViews();
    }

    public boolean isOwner(Article article, MemberCreate memberCreate) {

        Member member = memberService.findByUserId(memberCreate.getUserId());

        if (memberCreate == null)
            return false;

        return (article.getMember().getId() == member.getId());
    }
}
