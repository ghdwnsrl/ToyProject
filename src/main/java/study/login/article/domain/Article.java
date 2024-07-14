package study.login.article.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.login.common.BaseEntity;
import study.login.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {
    private Long id;

    private String title;
    private String contents;

    private Member member;

    private Integer views;

    @Builder
    public Article(Long id, String title, String contents,  Member member, Integer views) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.member = member;
        this.views = views;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void increaseViews(){
        this.views ++;
    }
}
