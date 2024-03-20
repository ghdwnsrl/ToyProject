package study.login.dto;

import lombok.Data;
import study.login.domain.Comment;

@Data
public class CommentListDto {
    private String writer;
    private String contents;

    public CommentListDto(Comment comment) {
        this.writer = comment.getUserId();
        this.contents = comment.getContents();
    }
}
