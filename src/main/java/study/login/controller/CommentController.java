package study.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.login.dto.CommentDto;
import study.login.service.CommentService;

@Controller @Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/write")
    public String writeComment(@ModelAttribute(name = "commentDto") CommentDto commentDto, RedirectAttributes rttr) {

        log.info("commentDto = {}", commentDto);
        commentService.write(commentDto);

        rttr.addFlashAttribute("fromComment", true);
        return "redirect:/article/read/"+commentDto.getArticleId();
    }

    @DeleteMapping("/comment/delete/{commentId}")
    public String deleteComment(@ModelAttribute(name = "commentId") Long commentId, Long articleId, RedirectAttributes rttr) {
        log.info("commentId = {}",commentId);
        log.info("articleId = {}",articleId);

        commentService.removeComment(commentId);

        rttr.addFlashAttribute("fromComment", true);
        return "redirect:/article/read/" + articleId;
    }
}