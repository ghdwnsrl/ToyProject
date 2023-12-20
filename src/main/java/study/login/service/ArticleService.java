package study.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.domain.Article;
import study.login.domain.Member;
import study.login.dto.ArticleWriteForm;
import study.login.dto.MemberDto;
import study.login.repository.ArticleRepository;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> findLists() {
        return articleRepository.findAll();
    }

    /**
     * 글 저장
     */
    public void write(ArticleWriteForm articleWriteForm , Member member) {


        articleRepository.save(
                new Article(
                        articleWriteForm.getTitle(),
                        articleWriteForm.getContents(),
                        member
                )
        );
    }

    // TODO : 저장, 수정, 삭제 기능 구현 남음
}
