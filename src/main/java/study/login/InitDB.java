package study.login;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.login.article.controller.port.ArticleService;
import study.login.article.domain.Article;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.Member;

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

//            Member member = Member.builder()
//                    .userId("hong")
//                    .password("hong")
//                    .nickname("jun")
//                    .build();
//
//            member = memberService.save(member);
//
//            Article article = Article.builder()
//                    .title("안녕하세요")
//                    .contents("저는 홍준기입니다")
//                    .build();
//
//            articleService.write(article);
//
//            createDummyArticle(member);
        }

        private void createDummyArticle(Member member) {

            for (int i = 0; i < 100; i++) {
                Article article = Article.builder()
                        .title("안녕하세요"+ i)
                        .contents("저는 홍준기입니다")
                        .build();

                articleService.write(article);
            }
        }

    }


}

