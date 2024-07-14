package study.login.article.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.login.article.controller.port.ArticleService;
import study.login.article.domain.Article;
import study.login.article.domain.ArticleDetailDto;
import study.login.article.domain.ArticleWriteForm;
import study.login.comment.controller.port.CommentService;
import study.login.comment.domain.CommentDto;
import study.login.comment.domain.CommentListDto;
import study.login.member.controller.port.MemberService;
import study.login.member.domain.LoginMember;
import study.login.member.domain.Member;
import study.login.session.SessionConst;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/write")
    public String write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, Model model) {

        model.addAttribute("articleWriteForm" , new ArticleWriteForm());
        model.addAttribute("loginMember", loginMember);


        return "writeArticleForm";
    }

    @PostMapping("/write")
    public String receiveArticle(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, @ModelAttribute ArticleWriteForm articleWriteForm) {

        Member findedMember = memberService.findByUserId(loginMember.getUserId());
        log.info("member.getId() = {}" , findedMember.getUserId() );
        articleService.write(articleWriteForm , findedMember);

        return "redirect:/";
    }

    @GetMapping("/article/read/{articleId}")
    public String readArticle(@PathVariable(name = "articleId") Long articleId,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                              Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Boolean fromComment = (Boolean) model.getAttribute("fromComment");

        Map<String, Object> readResult = articleService.read(articleId, loginMember, fromComment);

        ArticleDetailDto articleDetailDto = new ArticleDetailDto((Article) readResult.get("article"));

        List<CommentListDto> commentListDtos = commentService.requestCommentList(articleId);

        model.addAttribute("articleDetailDto", articleDetailDto);
        model.addAttribute("isOwner", readResult.get("isOwner"));
        model.addAttribute("page", page);
        model.addAttribute("commentDto", new CommentDto());
        model.addAttribute("commentListDtos", commentListDtos);

        return "readArticleForm";
    }

    @DeleteMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable(name = "articleId") Long articleId ) {

        commentService.deleteByArticleId(articleId);
        articleService.deleteById(articleId);

        return "redirect:/";
    }
    @PutMapping("/article/edit/{articleId}")
    public String edit(@PathVariable(name = "articleId") Long articleId, ArticleDetailDto articleDetailDto ) {

        log.info(articleDetailDto.toString());

        articleService.update(articleDetailDto);

        return "redirect:/";
    }

    @GetMapping("/article/edit/{articleId}")
    public String editForm(@PathVariable(name = "articleId") Long articleId, Model model) {
        Article article = articleService.findById(articleId).orElseThrow(NoSuchElementException::new);

        ArticleDetailDto articleDetailDto = new ArticleDetailDto(article);

        model.addAttribute("articleDetailDto", articleDetailDto);

        return "updateArticleForm";
    }
}
