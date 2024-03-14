package study.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.login.domain.Article;
import study.login.domain.Member;
import study.login.dto.ArticleDetailDto;
import study.login.dto.ArticleDto;
import study.login.dto.ArticleWriteForm;
import study.login.dto.MemberDto;
import study.login.service.ArticleService;
import study.login.service.MemberService;
import study.login.session.SessionConst;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

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
    public String readArticle(@PathVariable(name = "articleId") Long articleId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,  Model model) {

        Article article = articleService.findByArticleId(articleId).orElseThrow(NoSuchElementException::new);
        article.increaseArticle();
        boolean isOwner = articleService.isOwner(article,memberDto);

        log.info("글 주인 == 로그인한 사용자 : {}",isOwner);
        ArticleDetailDto articleDetailDto = ArticleDetailDto.builder()
                .title(article.getTitle())
                .views(article.getViews())
                .writer(article.getMember().getNickname())
                .writerId(article.getMember().getId())
                .contents(article.getContents())
                .id(article.getId())
                .build();

        model.addAttribute("articleDetailDto", articleDetailDto);
        model.addAttribute("isOwner", isOwner);

        return "readArticleForm";
    }

    @DeleteMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable(name = "articleId") Long articleId ) {

        log.info(articleId.toString());

        articleService.deleteArticle(articleId);

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
