package study.login;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.login.domain.Article;
import study.login.domain.Member;
import study.login.service.ArticleService;
import study.login.service.MemberService;

import java.math.BigDecimal;

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
                    .title("안녕하세용")
                    .contents("저는 홍준기입니다")
                    .member(member)
                    .build();

            articleService.write(article);


        }

    }


}

