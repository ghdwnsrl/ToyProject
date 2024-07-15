package study.login.comment.infrastructure;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import study.login.comment.domain.Comment;
import study.login.comment.service.port.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Builder
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public void deleteByArticleId(Long articleId) {
        commentJpaRepository.deleteByArticleEntityId(articleId);
    }

    @Override
    public List<Comment> findByArticleId(Long id) {
        return commentJpaRepository.findByArticleEntityId(id)
                .stream().map(CommentEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).toModel();
    }

    @Override
    public void deleteById(Long id) {
        commentJpaRepository.deleteById(id);
    }
}
