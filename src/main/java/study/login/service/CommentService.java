package study.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.login.domain.Article;
import study.login.domain.Comment;
import study.login.domain.Member;
import study.login.dto.CommentDto;
import study.login.dto.CommentListDto;
import study.login.repository.CommentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    public List<CommentListDto> requestCommentList(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream().map(c -> new CommentListDto(c))
                .collect(Collectors.toList());
    }

    public void write(CommentDto commentDto) {
        Article article = articleService.findByArticleId(commentDto.getArticleId()).orElseThrow(NoSuchElementException::new);

        Comment createdComment = new Comment(article, commentDto.getNickname(), commentDto.getContents());

        commentRepository.save(createdComment);
    }

    public void deleteByArticleId(Long articleId) {
        commentRepository.deleteComments(articleId);
    }
}
