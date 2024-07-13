package study.login.comment.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.login.article.domain.Article;
import study.login.common.BaseEntity;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    private Long id;

    private Article article;

    private String nickname;

    private String contents;

    @Builder
    public Comment(Long id, Article article, String nickname, String contents) {
        this.id = id;
        this.article = article;
        this.nickname = nickname;
        this.contents = contents;
    }
}