package study.login.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import study.login.domain.Article;

@Data
@ToString
public class ArticleDetailDto {

    private Long id;
    private String title;
    private String writer;
    private Long writerId;
    private String contents;
    private Integer views;

    @Builder
    public ArticleDetailDto(Long id, String title, String writer, Long writerId, String contents, Integer views) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.writerId = writerId;
        this.contents = contents;
        this.views = views;
    }

    public ArticleDetailDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.writer = article.getMember().getNickname();
        this.writerId = article.getMember().getId();
        this.contents = article.getContents();
        this.views = article.getViews();
    }


    public boolean isOwner(Long currentUserId) {
        return ( writerId == currentUserId);
    }
}
