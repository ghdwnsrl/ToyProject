package study.login.comment.service;

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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    public List<CommentListDto> requestCommentList(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream().map(CommentListDto::new)
                .collect(Collectors.toList());
    }

    public void write(CommentDto commentDto) {
        Article article = articleService.findByArticleId(commentDto.getArticleId()).orElseThrow(NoSuchElementException::new);

        Comment createdComment = Comment.builder()
                .article(article)
                .contents(commentDto.getContents())
                .nickname(commentDto.getNickname())
                .build();

        commentRepository.save(createdComment);
    }

    public void deleteByArticleId(Long articleId) {
        commentRepository.deleteComments(articleId);
    }

    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
