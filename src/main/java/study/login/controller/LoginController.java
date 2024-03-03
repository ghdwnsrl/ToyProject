package study.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.login.domain.Member;
import study.login.dto.ArticleDto;
import study.login.dto.LoginFormDto;
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

    @GetMapping("/login")
    public String login(Model model, LoginFormDto loginFormDto) {
        return "/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginFormDto") LoginFormDto loginFormDto , BindingResult bindingResult, HttpServletRequest request , Model model) {

        if (bindingResult.hasErrors()) {
            return "/login";
        }

        MemberDto loginUser = loginService.login(loginFormDto.getUserId(), loginFormDto.getPassword());

        // 로그인 성공
        if (loginUser != null){

            List<ArticleDto> articleDtos = articleDtoService.findArticleDtos();
            model.addAttribute("articleDtos", articleDtos);

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER , loginUser);

            return "redirect:/";

        } else { // 로그인 실패
            return "redirect:/login";
        }

    }
}