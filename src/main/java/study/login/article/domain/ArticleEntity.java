package study.login.article.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.login.common.BaseEntity;
import study.login.member.infrastructure.MemberEntity;

@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleEntity extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    private Integer views;

    @Builder
    public ArticleEntity(Long id, String title, String contents, MemberEntity memberEntity, Integer views) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.memberEntity = memberEntity;
        this.views = views;
    }

    public static ArticleEntity from(Article article) {
        return ArticleEntity.builder()
                .id(article.getId())
                .title(article.getTitle())
                .contents(article.getContents())
                .memberEntity(MemberEntity.from(article.getMember()))
                .views(article.getViews())
                .build();
    }

    public Article toModel() {
        return Article.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .member(memberEntity.toModel())
                .views(views)
                .build();
    }
}
