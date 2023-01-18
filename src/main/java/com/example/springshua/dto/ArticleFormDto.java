package com.example.springshua.dto;

import com.example.springshua.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleFormDto {

    private String title;
    private String content;


    public Article toEntity() {
        return new Article(null, title, content);
    }
}
