package study.login.article.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ArticleWriteForm {
    private String title;
    private String contents;

    public ArticleWriteForm(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
