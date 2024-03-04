package study.login.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor
public class Article {
    @Id @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int views;

    @Builder
    public Article(String title, String contents, Member member) {
        this.title = title;
        this.contents = contents;
        this.member = member;
        this.views = 0;
    }
}
