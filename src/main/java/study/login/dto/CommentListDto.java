package study.login.dto;

import lombok.Data;
import study.login.domain.Comment;

@Data
public class CommentListDto {
    private Long id;
    private String writer;
    private String contents;

    public CommentListDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getUserId();
        this.contents = comment.getContents();
    }
}
