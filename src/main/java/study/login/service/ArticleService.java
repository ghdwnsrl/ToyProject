package study.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.domain.Article;
import study.login.domain.Member;
import study.login.dto.ArticleDetailDto;
import study.login.dto.ArticleDto;
import study.login.dto.ArticleWriteForm;
import study.login.dto.MemberDto;
import study.login.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service @Transactional @Slf4j
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

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

    public void write(ArticleWriteForm articleWriteForm , Member member) {

        articleRepository.save(
                new Article(
                        articleWriteForm.getTitle(),
                        articleWriteForm.getContents(),
                        member
                )
        );
    }

    public void write(Article article){
        articleRepository.save(article);
    }

    public Optional<Article> findByArticleId(Long articleId) {
        return articleRepository.findById(articleId);
    }

    public void deleteArticle(Long articleId) {

        Article article = findByArticleId(articleId).orElseThrow(NoSuchElementException::new);

        articleRepository.delete(article);

    }

    public void update(ArticleDetailDto articleDetailDto) {

        Article article = articleRepository.findById(articleDetailDto.getId()).orElseThrow(NoSuchElementException::new);

        article.articleUpdate(articleDetailDto.getTitle(),articleDetailDto.getContents());
    }

    public boolean isOwner(Article article, MemberDto memberDto) {

        if (memberDto == null)
            return false;

        return (article.getMember().getId() == memberDto.getId());
    }
}
