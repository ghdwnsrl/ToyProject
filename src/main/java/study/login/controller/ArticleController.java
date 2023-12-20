package study.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.login.domain.Member;
import study.login.dto.ArticleWriteForm;
import study.login.dto.MemberDto;
import study.login.service.ArticleService;
import study.login.service.MemberService;
import study.login.session.SessionConst;

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

        return "/writeArticleForm";
    }

    @PostMapping("/write")
    public String receiveArticle(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,  @ModelAttribute ArticleWriteForm articleWriteForm) {

        System.out.println("articleWriteForm.getTitle() = " + articleWriteForm.getTitle());
        System.out.println("articleWriteForm.getContents() = " + articleWriteForm.getContents());


        Member findedMember = memberService.findMember(memberDto.getUserId());
        log.info("member.getId() = {}" , findedMember.getUserId() );
        articleService.write(articleWriteForm , findedMember);

        return "redirect:/list";

    }

}
