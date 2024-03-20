package study.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.login.dto.CommentDto;
import study.login.service.CommentService;

@Controller @Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/write")
    public String writeComment(@ModelAttribute(name = "commentDto") CommentDto commentDto) {

        log.info("commentDto = {}", commentDto);
        commentService.write(commentDto);

        return "redirect:/article/read/"+commentDto.getArticleId();
    }
}