package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스트 된다
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 예상
        Article article1 = new Article(1L, "title1", "content1");
        Article article2 = new Article(2L, "title2", "content2");
        Article article3 = new Article(3L, "title3", "content3");
        List<Article> expected = new ArrayList<>(Arrays.asList(article1, article2, article3));

        // 실제
        List<Article> articles = articleService.index();

        // 비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_pass_inputValidId() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "title1", "content1");

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_fail_inputInvalidId() {
        // 예상
        Long id = -1L;
        Article expected = null;

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_pass_inputWithTitleAndContent() {
        // 예상
        String title = "test title";
        String content = "test content";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_fail_inputWithId() {
        // 예상
        String title = "test title";
        String content = "test content";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_pass_inputWithValidIdAndTitleAndContent() {
        // 예상
        String title = "test title";
        String content = "test content";
        ArticleForm dto = new ArticleForm(3L, title, content);
        Article expected = new Article(3L, "test title", "test content");

        // 실제
        Article article = articleService.update(3L, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_pass_inputWithValidIdAndTitle() {
        // 예상
        String title = "test title";
        ArticleForm dto = new ArticleForm(3L, title, null);
        Article expected = new Article(3L, "test title", null);

        // 실제
        Article article = articleService.update(3L, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_fail_inputWithInvalidId() {
        // 예상
        String title = "test title";
        String content = "test content";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.update(4L, dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_pass_inputWithValidId() {
        // 예상
        Article expected = new Article(3L, "title3", "content3");

        // 실제
        Article article = articleService.delete(3L);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_fail_inputWithInvalidId() {
        // 예상
        Article expected = null;

        // 실제
        Article article = articleService.delete(4L);

        // 비교
        assertEquals(expected, article);
    }
}