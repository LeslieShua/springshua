package com.example.springshua.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@ToString
@Entity // 얘를 선언해 주어야 DB가 해당 객체를 인식 가능
@AllArgsConstructor
@NoArgsConstructor // default 생성자 파라메타가 없음
public class Article {

    @Id // 대표 값 주민번호 같은 것
    @GeneratedValue // Id 값 자동 생성 1, 2, 3 ..
    private Long id;

    @Column
    private String title;
    @Column
    private String content;


}