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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String join(@ModelAttribute("memberDto") @Validated MemberDto memberDto , RedirectAttributes redirectAttributes) {

        Member member = new Member(
                memberDto.getUserId(),
                memberDto.getPassword(),
                memberDto.getNickname()
        );

        log.info(member.getNickname());

        /**
         * 이미 가입된 회원인 경우, 알림 메시지 띄우기
         */
        Member joinedMember = memberService.join(member);
        /**
         *  실패한 경우,
         */
        if (joinedMember == null) {
            redirectAttributes.addAttribute("status" , false);
            return "redirect:/member/join";
        }

        return "redirect:/login";
    }


}
