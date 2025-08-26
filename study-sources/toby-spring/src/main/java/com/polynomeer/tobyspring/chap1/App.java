package com.polynomeer.tobyspring.chap1;

import com.polynomeer.tobyspring.chap1.dao.UserDao;
import com.polynomeer.tobyspring.chap1.domain.User;
import com.polynomeer.tobyspring.chap1.factory.DaoFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@SpringBootApplication
@Import(DaoFactory.class) // 설정 클래스(빈 등록) 가져오기
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // 애플리케이션 시작 시 테이블 생성 + 간단 동작 검증
    // DataSource와 UserDao는 스프링이 주입
    @org.springframework.context.annotation.Bean
    ApplicationRunner demo(DataSource dataSource, UserDao userDao) {
        return args -> {
            // 1) 스키마 준비 (DataSource 기반)
            Schema.createUsers(dataSource);

            // 2) 동작 검증
            userDao.deleteAll();
            System.out.println("count after deleteAll = " + userDao.getCount()); // 0

            User u1 = new User();
            u1.setId("u100");
            u1.setName("토비");
            u1.setPassword("spring");
            User u2 = new User();
            u2.setId("u200");
            u2.setName("스프링");
            u2.setPassword("book");

            userDao.add(u1);
            userDao.add(u2);
            System.out.println("count after inserts = " + userDao.getCount());   // 2

            User loaded = userDao.get("u100");
            System.out.println("loaded = " + loaded.getId() + ", " + loaded.getName() + ", " + loaded.getPassword());
            System.out.println("OK: 1.5 Spring IoC 실습 성공");
        };
    }
}
