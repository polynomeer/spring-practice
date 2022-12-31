package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // DB가 해당 객체를 인식하도록 함
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Article {

    @Id // 대표값을 지정, like 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3, ... 자동 생성 애너테이션, DB가 id를 자동생성
    private Long id;

    @Column
    private String title;

    @Column
    private String content;
}
