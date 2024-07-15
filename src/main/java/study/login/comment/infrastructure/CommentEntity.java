package study.login.comment.infrastructure;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import study.login.article.infrastructure.ArticleEntity;
import study.login.comment.domain.Comment;
import study.login.common.BaseEntity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity articleEntity;

    private String nickname;

    private String contents;

    @Builder
    public CommentEntity(Long id, ArticleEntity articleEntity, String nickname, String contents) {
        this.id = id;
        this.articleEntity = articleEntity;
        this.nickname = nickname;
        this.contents = contents;
    }

    public static CommentEntity from(Comment comment) {
        return CommentEntity.builder()
                .id(comment.getId())
                .articleEntity(ArticleEntity.from(comment.getArticle()))
                .contents(comment.getContents())
                .nickname(comment.getNickname())
                .build();
    }

    public Comment toModel() {
        return Comment.builder()
                .id(id)
                .contents(contents)
                .article(articleEntity.toModel())
                .nickname(nickname)
                .build();
    }
}