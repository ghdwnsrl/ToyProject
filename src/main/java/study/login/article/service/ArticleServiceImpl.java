package study.login.article.service;

import lombok.Builder;
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
import study.login.member.domain.LoginMember;
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;

import java.util.*;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberService memberService;


    @Override
    public Map<String, Object> read(Long articleId, LoginMember loginMember, Boolean fromComment) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(NoSuchElementException::new);

        if (fromComment == null || !fromComment){
            article.increaseViews();
            article = articleRepository.save(article);
        }

        Map<String, Object> map = new HashMap<>();

        boolean owner = isOwner(article.getMember().getUserId(), loginMember);

        map.put("article", article);
        map.put("isOwner", owner);

        return map;
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> findLists(int page) {

        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return articleRepository.findAll(pageable).map(m -> new ArticleDto(
                m.getId(),
                m.getTitle(),
                m.getMember().getNickname(),
                m.getViews()
        ));
    }

    @Transactional
    public Article write(ArticleWriteForm articleWriteForm , Member member) {

        Article article = Article.builder()
                .title(articleWriteForm.getTitle())
                .contents(articleWriteForm.getContents())
                .member(member)
                .views(0)
                .build();

        return articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public Optional<Article> findById(Long articleId) {
        return articleRepository.findById(articleId);
    }

    @Transactional
    public void deleteById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional
    public Article update(ArticleDetailDto articleDetailDto) {

        Article article = articleRepository.findById(articleDetailDto.getId()).orElseThrow(UserNotFoundException::new);

        article.update(
                articleDetailDto.getTitle(),
                articleDetailDto.getContents()
        );

        return articleRepository.save(article);
    }

    private boolean isOwner(String articleUserId, LoginMember loginMember) {
        boolean owner = false;
        if (loginMember != null) {
            owner = articleUserId.equals(loginMember.getUserId());
        }
        return owner;
    }
}
