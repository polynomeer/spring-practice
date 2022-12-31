package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j // 로깅을 위한 애너테이션
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info("{}", form);

        // 1. DTO를 변환 to Entity
        Article article = form.toEntity();
        log.info("{}", article);

        // 2. Repository에게 Entity를 DB안에 저장하게 한다.
        Article saved = articleRepository.save(article);
        log.info("{}", saved);

        return "";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id : {}", id);

        // 1. id로 데이터를 가져온다.
        Article article = articleRepository.findById(id).orElse(null);

        // 2. 가져온 데이터를 모델에 등록한다.
        model.addAttribute("article", article);

        // 3. 보여줄 페이지를 설정한다.
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {

        List<Article> articles = articleRepository.findAll();
        // 1. 모든 Article을 가져온다.

        // 2. 가져온 Article 묶음을 뷰로 전달한다.
        model.addAttribute("articleList", articles);

        // 3. 뷰 페이지를 설정한다.
        return "articles/index";
    }
}
