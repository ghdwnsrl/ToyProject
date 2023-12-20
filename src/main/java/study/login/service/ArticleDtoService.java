package study.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.login.domain.Article;
import study.login.dto.ArticleDto;
import study.login.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleDtoService {

    private final ArticleRepository articleRepository;

    public List<ArticleDto> findArticleDtos() {
        return articleRepository.findAll().stream()
                .map((Article article) ->
                        new ArticleDto(article.getId(), article.getTitle(), article.getMember().getNickname() , article.getViews()))
                .collect(Collectors.toList());
    }
}
