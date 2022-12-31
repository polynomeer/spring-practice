package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @GeneratedValue // 1, 2, 3, ...
    private Long id;

    @Column
    private String title;

    @Column
    private String content;
}
