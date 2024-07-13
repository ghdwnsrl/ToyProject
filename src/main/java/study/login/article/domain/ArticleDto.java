package study.login.article.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ArticleDto {

    private Long id;
    private String title;
    private String writer;
    private Integer views;

    @Builder
    public ArticleDto(Long id, String title, String writer, Integer views) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.views = views;
    }
}
