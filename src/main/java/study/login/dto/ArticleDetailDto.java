package study.login.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

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

    public boolean isOwner(Long currentUserId) {
        return ( writerId == currentUserId);
    }
}
