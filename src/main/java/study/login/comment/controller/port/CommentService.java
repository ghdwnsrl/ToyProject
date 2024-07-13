package study.login.comment.controller.port;

import study.login.comment.domain.CommentDto;
import study.login.comment.domain.CommentListDto;

import java.util.List;

public interface CommentService {

    List<CommentListDto> requestCommentList(Long articleId);

    void write(CommentDto commentDto);

    void deleteByArticleId(Long articleId);

    void removeComment(Long commentId);


}
