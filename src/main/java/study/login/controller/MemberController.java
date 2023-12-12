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
import study.login.dto.MemberDto;
import study.login.service.MemberService;

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

    @PostMapping("/login")
    public String login(@ModelAttribute("memberDto") MemberDto memberDto) {

        String userId = memberDto.getUserId();
        String password = memberDto.getPassword();

        boolean loginResult = memberService.login(userId, password);

        log.info("loginResult = " + loginResult);

        /**
         * 로그인 성공 -> 다음 화면
         * 로그인 실패 -> 로그인 화면 Redirect.
         */

        if (loginResult)
            return "다음 화면";
        else
            return "redirect:/";
    }
}
