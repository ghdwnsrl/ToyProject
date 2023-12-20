package study.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.login.domain.Member;
import study.login.dto.ArticleDto;
import study.login.dto.MemberDto;
import study.login.service.ArticleDtoService;
import study.login.service.MemberService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/join")
    public String join(Model model, MemberDto memberDto) {
        model.addAttribute("memberDto" , memberDto);
        return "member/memberJoinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("memberDto") @Validated MemberDto memberDto) {

        Member member = new Member(
                memberDto.getUserId(),
                memberDto.getPassword(),
                memberDto.getNickname()
        );

        log.info(member.getNickname());

        memberService.join(member);

        return "redirect:/";
    }


}
