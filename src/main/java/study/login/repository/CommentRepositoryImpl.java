package study.login.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import study.login.domain.QComment;

import static study.login.domain.QComment.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteComments(Long articleId) {
        queryFactory
                .delete(comment)
                .where(comment.article.id.eq(articleId))
                .execute();
    }
}
