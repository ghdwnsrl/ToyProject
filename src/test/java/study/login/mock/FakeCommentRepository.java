package study.login.mock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.login.article.domain.Article;
import study.login.article.service.port.ArticleRepository;
import study.login.comment.domain.Comment;
import study.login.comment.service.port.CommentRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeCommentRepository implements CommentRepository {

    private final List<Comment> data = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public void deleteByArticleId(Long articleId) {
        data.removeIf(comment -> comment.getArticle().getId().equals(articleId));
    }

    @Override
    public List<Comment> findByArticleId(Long id) {
        return data.stream()
                .filter(comment -> comment.getArticle().getId().equals(id))
                .collect(Collectors.toList());
    }

    public Optional<Comment> findById(Long id) {
        return data.stream()
                .filter(comment -> comment.getId().equals(id))
                .findAny();
    }


    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null || comment.getId() == 0) {
            Comment newComment = Comment.builder()
                    .id(sequence.incrementAndGet())
                    .nickname(comment.getNickname())
                    .contents(comment.getContents())
                    .article(comment.getArticle())
                    .build();

            data.add(newComment);
            return newComment;
        } else {
            data.removeIf(m -> Objects.equals(m.getId(), comment.getId()));
            data.add(comment);
            return comment;
        }
    }

    @Override
    public void deleteById(Long id) {
        data.removeIf(comment -> comment.getId().equals(id));
    }

}