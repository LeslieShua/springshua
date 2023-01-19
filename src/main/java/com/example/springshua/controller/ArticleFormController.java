package com.example.springshua.controller;

import com.example.springshua.dto.ArticleFormDto;
import com.example.springshua.entity.Article;
import com.example.springshua.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Slf4j
@Controller
public class ArticleFormController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createPostArticle(ArticleFormDto form) {
        log.info(form.toString());

        // 1. Dto를 Entity로 변환  Dto -> Entity
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "";
    }

    //DB 데이터 조회 -> 웹에서 확인하기
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){  //id는 URL주소(path)로 부터 입력되므로 @PathVariable를 적어줍니다.
        log.info("id =" + id);


        // 1: id로 데이터를 찾아서 이를 Entity의 묶음, List로 반환한다.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2: 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3: 보여줄 페이지를 설정
        return "articles/show";
    }
}
