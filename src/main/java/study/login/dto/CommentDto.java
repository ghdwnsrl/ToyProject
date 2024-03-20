package study.login.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long articleId;
    private String nickname;
    private String contents;
}
