package study.login.comment.domain;

import lombok.Data;

@Data
public class CommentListDto {
    private Long id;
    private String writer;
    private String contents;

    public CommentListDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getNickname();
        this.contents = comment.getContents();
    }
}
