package study.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.login.dto.ArticleDto;
import study.login.dto.MemberDto;
import study.login.service.ArticleDtoService;
import study.login.service.LoginService;
import study.login.service.MemberService;
import study.login.session.SessionConst;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final ArticleDtoService articleDtoService;

    @PostMapping("/login")
    public String login(@ModelAttribute("memberDto") MemberDto memberDto , HttpServletRequest request , Model model /*, @RequestParam(defaultValue = "/") String redirectURL*/) {


        MemberDto loginUser = loginService.login(memberDto.getUserId(), memberDto.getPassword());

        log.info("loginUser.getUserId()={}",loginUser.getUserId());

        if (loginUser != null){
            List<ArticleDto> articleDtos = articleDtoService.findArticleDtos();
            model.addAttribute("articleDtos", articleDtos);

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER , loginUser);

        } else {
            return "redirect:/";
        }
    }
}