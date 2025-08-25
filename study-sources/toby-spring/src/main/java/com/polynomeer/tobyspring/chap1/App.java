package com.polynomeer.tobyspring.chap1;

import com.polynomeer.tobyspring.chap1.dao.UserDao;
import com.polynomeer.tobyspring.chap1.domain.User;
import com.polynomeer.tobyspring.chap1.factory.ConnectionMaker;
import com.polynomeer.tobyspring.chap1.factory.MysqlConnectionMaker;

public class App {
    public static void main(String[] args) throws Exception {
        // 1) MySQL로 테스트한다면
        ConnectionMaker cm = new MysqlConnectionMaker();
        // 2) H2로 빠르게 돌리고 싶다면
        // ConnectionMaker cm = new H2ConnectionMaker();
        UserDao dao = new UserDao(cm);

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);
        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
    }
}
