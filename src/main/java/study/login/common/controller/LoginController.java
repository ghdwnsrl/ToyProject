package study.login.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.login.common.dto.LoginFormDto;
import study.login.common.exception.InvalidLoginException;
import study.login.common.exception.UserNotFoundException;
import study.login.common.service.LoginService;
import study.login.member.domain.MemberCreate;
import study.login.session.SessionConst;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(Model model, LoginFormDto loginFormDto) {

        return "/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginFormDto") LoginFormDto loginFormDto , BindingResult bindingResult,
                        HttpServletRequest request , Model model, @RequestParam(name = "redirectURL", defaultValue = "/") String redirectURL) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        MemberCreate loginUser = null;

        try {
            loginUser = loginService.login(loginFormDto.getUserId(), loginFormDto.getPassword());
        } catch (UserNotFoundException e) {
            bindingResult.reject("login.noUser");
        } catch (InvalidLoginException e) {
            bindingResult.reject("login.noMatch");
        }


        // 로그인 실패
        if(loginUser == null)  {
            return "login";
        }

        // 로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER , loginUser);

        log.info("[Post] login - redirectURL {}", redirectURL);
        // redirect 요청 처리
        if (StringUtils.hasText(redirectURL)) {
            return "redirect:" + redirectURL;
        }

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

}