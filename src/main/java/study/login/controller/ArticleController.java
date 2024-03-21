package study.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.login.domain.Article;
import study.login.domain.Member;
import study.login.dto.*;
import study.login.service.ArticleService;
import study.login.service.CommentService;
import study.login.service.MemberService;
import study.login.session.SessionConst;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/write")
    public String write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto, Model model) {

        model.addAttribute("articleWriteForm" , new ArticleWriteForm());
        model.addAttribute("loginMember", memberDto);


        return "writeArticleForm";
    }

    @PostMapping("/write")
    public String receiveArticle(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,  @ModelAttribute ArticleWriteForm articleWriteForm) {

        System.out.println("articleWriteForm.getTitle() = " + articleWriteForm.getTitle());
        System.out.println("articleWriteForm.getContents() = " + articleWriteForm.getContents());

        Member findedMember = memberService.findMember(memberDto.getUserId());
        log.info("member.getId() = {}" , findedMember.getUserId() );
        articleService.write(articleWriteForm , findedMember);

        return "redirect:/";
    }

    @GetMapping("/article/read/{articleId}")
    public String readArticle(@PathVariable(name = "articleId") Long articleId,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                              Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Article article = articleService.findByArticleId(articleId).orElseThrow(NoSuchElementException::new);
        articleService.increaseArticleViews(article);

        boolean isOwner = articleService.isOwner(article,memberDto);

        ArticleDetailDto articleDetailDto = ArticleDetailDto.builder()
                .title(article.getTitle())
                .views(article.getViews())
                .writer(article.getMember().getNickname())
                .writerId(article.getMember().getId())
                .contents(article.getContents())
                .id(article.getId())
                .build();

        List<CommentListDto> commentListDtos = commentService.requestCommentList(articleId);
        log.info("commentListDtos ={}", commentListDtos);


        model.addAttribute("articleDetailDto", articleDetailDto);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("page", page);
        model.addAttribute("commentDto", new CommentDto());
        model.addAttribute("commentListDtos", commentListDtos);

        return "readArticleForm";
    }

    @DeleteMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable(name = "articleId") Long articleId ) {

        log.info("deleteArticle 시작");
        commentService.deleteByArticleId(articleId);
        articleService.deleteArticle(articleId);
        log.info("deleteArticle 끝");

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
        Article article = articleService.findByArticleId(articleId).orElseThrow(NoSuchElementException::new);

        ArticleDetailDto articleDetailDto = ArticleDetailDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .contents(article.getContents())
                .writer(article.getMember().getNickname())
                .views(article.getViews())
                .build();

        model.addAttribute("articleDetailDto", articleDetailDto);

        return "updateArticleForm";
    }
}
