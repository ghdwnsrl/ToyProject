package study.login.comment.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.article.controller.port.ArticleService;
import study.login.article.domain.Article;
import study.login.comment.controller.port.CommentService;
import study.login.comment.domain.Comment;
import study.login.comment.domain.CommentDto;
import study.login.comment.domain.CommentListDto;
import study.login.comment.service.port.CommentRepository;
import study.login.common.exception.CommentNotFoundException;
import study.login.common.exception.PermissionDeniedException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    @Transactional(readOnly = true)
    public List<CommentListDto> findList(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream().map(CommentListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Comment write(CommentDto commentDto) {
        Article article = articleService.findById(commentDto.getArticleId()).orElseThrow(NoSuchElementException::new);

        Comment comment = Comment.builder()
                .article(article)
                .contents(commentDto.getContents())
                .nickname(commentDto.getNickname())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteByArticleId(Long articleId) {
        commentRepository.deleteByArticleId(articleId);
    }

    @Transactional
    public void deleteById(Long commentId, String nickname) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (!comment.getNickname().equals(nickname))
            throw new PermissionDeniedException();

        commentRepository.deleteById(commentId);
    }
}
