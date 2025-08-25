package com.polynomeer.tobyspring.chap1;

import com.polynomeer.tobyspring.chap1.dao.UserDao;
import com.polynomeer.tobyspring.chap1.domain.User;
import com.polynomeer.tobyspring.chap1.factory.DaoFactory;

public class App {
    public static void main(String[] args) throws Exception {
        DaoFactory factory = new DaoFactory();

        // IoC 포인트: 클라이언트는 “공장”만 알고, 내부 조립은 모름
        UserDao userDao = factory.userDao();

        // 실습 편의: 테이블 생성
        Schema.createUsers(factory.connectionMaker());

        // 동작 검증
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
        System.out.println("OK: 1.4 IoC + DaoFactory 실습 성공");
    }
}
