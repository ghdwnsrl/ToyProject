package study.login.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.login.article.domain.ArticleDto;
import study.login.article.service.ArticleServiceImpl;

@Controller
@RequiredArgsConstructor @Slf4j
public class BoardController {

    private final ArticleServiceImpl articleService;

    @GetMapping("/")
    public String show(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<ArticleDto> articleList = articleService.findLists(page);

        for (ArticleDto articleDto : articleList) {
            log.info("articleDto : {}" ,articleDto.getId());
        }

        model.addAttribute("articleDtos",articleList);
        model.addAttribute("maxPage",5);

        model.addAttribute("page", page);

        return "/board";
    }
}
