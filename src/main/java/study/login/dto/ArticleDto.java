package study.login.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ArticleDto {

    private Long id;
    private String title;
    private String writer;
    private int views;

    public ArticleDto(Long id, String title, String writer, int views) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.views = views;
    }
}
