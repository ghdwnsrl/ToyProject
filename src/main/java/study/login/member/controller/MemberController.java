package study.login.member.controller;

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
import study.login.member.domain.Member;
import study.login.member.domain.MemberCreate;
import study.login.member.service.MemberServiceImpl;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;


    @GetMapping("/join")
    public String join(Model model, MemberCreate memberCreate) {
        model.addAttribute("memberDto" , memberCreate);
        return "member/memberJoinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("memberDto") @Validated MemberCreate memberCreate, RedirectAttributes redirectAttributes) {


        Member joinedMember = memberServiceImpl.join(memberCreate);

        if (joinedMember == null) {
            redirectAttributes.addAttribute("status" , false);
            return "redirect:/member/join";
        }

        return "redirect:/login";
    }


}
