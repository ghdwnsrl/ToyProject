package study.login;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.login.domain.Article;
import study.login.domain.Member;
import study.login.service.ArticleService;
import study.login.service.MemberService;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberService memberService;
        private final ArticleService articleService;

        public void dbInit() {

            Member member = Member.builder()
                    .userId("hong")
                    .password("hong")
                    .nickname("jun")
                    .build();

            memberService.saveUser(member);

            Article article = Article.builder()
                    .title("안녕하세요")
                    .contents("저는 홍준기입니다")
                    .member(member)
                    .build();

            articleService.write(article);

            createDummyArticle(member);
        }

        private void createDummyArticle(Member member) {

            for (int i = 0; i < 100; i++) {
                Article article = Article.builder()
                        .title("안녕하세요"+String.valueOf(i))
                        .contents("저는 홍준기입니다")
                        .member(member)
                        .build();

                articleService.write(article);
            }
        }

    }


}

