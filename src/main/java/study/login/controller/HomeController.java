package study.login.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.login.dto.MemberDto;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @RequestParam(name = "redirectURL", defaultValue = "/list") String redirectURL) {


        model.addAttribute("redirectURL" , redirectURL);
        model.addAttribute("memberDto" , new MemberDto());

        return "/main";
    }

}
