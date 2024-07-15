package study.login.comment.controller.port;

import study.login.comment.domain.Comment;
import study.login.comment.domain.CommentDto;
import study.login.comment.domain.CommentListDto;

import java.util.List;

public interface CommentService {

    List<CommentListDto> findList(Long articleId);

    Comment write(CommentDto commentDto);

    void deleteByArticleId(Long articleId);

    void deleteById(Long commentId);


}
