package study.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.login.domain.Article;
import study.login.dto.ArticleDto;
import study.login.repository.ArticleRepository;
import study.login.service.ArticleDtoService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ArticleDtoService articleDtoService;

    @GetMapping("/list")
    public String show(Model model) {

        List<ArticleDto> articleList = articleDtoService.findArticleDtos();

        for (ArticleDto articleDto : articleList) {
            System.out.println("articleDto.getTitle() = " + articleDto.getTitle());
            System.out.println("articleDto.getWriter() = " + articleDto.getWriter());
        }

        model.addAttribute("articleDtos",articleList);

        return "/board";
    }
}
