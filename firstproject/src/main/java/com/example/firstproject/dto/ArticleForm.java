package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {

    private final String title;
    private final String content;

    public Article toEntity() {
        return new Article(null, this.title, this.content);
    }
}
