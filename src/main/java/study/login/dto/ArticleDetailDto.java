package study.login.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ArticleDetailDto {

    private Long id;
    private String title;
    private String writer;
    private String contents;
    private int views;

    @Builder
    public ArticleDetailDto(Long id, String title, String writer, String contents, int views) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.views = views;
    }
}
