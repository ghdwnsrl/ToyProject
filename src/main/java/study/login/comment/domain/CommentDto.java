package study.login.comment.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long articleId;
    private String nickname;
    private String contents;

    @Builder
    public CommentDto(Long articleId, String nickname, String contents) {
        this.articleId = articleId;
        this.nickname = nickname;
        this.contents = contents;
    }
}