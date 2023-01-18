package com.example.springshua.repository;

import com.example.springshua.entity.Article;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @NonNull
    @Override
    ArrayList<Article> findAll();
}
