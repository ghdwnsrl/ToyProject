package study.login.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleWriteForm {
    private String title;
    private String contents;
}
