package study.login.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import study.login.domain.QComment;

import static study.login.domain.QComment.*;

@Slf4j
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteComments(Long articleId) {
        log.info("delete comments 시작");
        queryFactory
                .delete(comment)
                .where(comment.article.id.eq(articleId))
                .execute();
        log.info("delete comments 끝");
    }
}
