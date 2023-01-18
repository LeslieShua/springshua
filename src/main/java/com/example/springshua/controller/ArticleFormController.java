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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleFormController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }


    //////////////
    @PostMapping("/articles/create") //url에 보여질 주소
    public String createPostArticle(ArticleFormDto form) {
        log.info(form.toString());

        // 1. Dto를 Entity로 변환  Dto -> Entity
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    //DB 데이터 조회
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){  //id는 URL주소(path)로 부터 입력되므로 @PathVariable를 적어줍니다.
        log.info("id =" + id);


        // 1: id로 데이터를 찾아서 이를 Entity의 묶음, List로 반환한다.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2: 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3: 보여줄 페이지를 설정 (상세 페이지)
        return "articles/show";
    }

    //데이터 목록 조회
    @GetMapping("/articles")
    public String index(Model model) {

        // 1: 모든 Article을 가져온다
        List<Article> articleEntityList = articleRepository.findAll();

        // 2: 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList", articleEntityList);

        // 3: 뷰 페이지를 설정
        return "articles/index"; // articles/index.mustache
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    //기존 데이터 Edit
    @PostMapping("/articles/update")
    public String update(ArticleFormDto form) {
        log.info(form.toString());

        // 1: DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2: 엔티티를 DB로 저장
        // 2-1: DB에 있는 ID를 받아와서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2: 수정할게 있다면 기존 데이터 값을 갱신
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티가 DB로 갱신!
        }

        // 3: 수정 결과 페이지로 리타이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rt) {
        log.info("삭제 Request 들어옴");
        
        // 1.: 삭제 대상을 가져옴
        Article target = articleRepository.findById(id).orElse(null);
        // 2: 대상을 삭제
        if (target != null) {
            articleRepository.delete(target);
            rt.addFlashAttribute("msg", id + "번 게시물 삭제 완료 했습니다!");
        }
        // 3: 결과 페이지로 리다이렉
        return "redirect:/articles";
    }
}