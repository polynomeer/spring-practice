package com.polynomeer.tobyspring.chap1;

import com.polynomeer.tobyspring.chap1.dao.UserDao;
import com.polynomeer.tobyspring.chap1.dao.UserDaoMysql;
import com.polynomeer.tobyspring.chap1.domain.User;

public class App {
    public static void main(String[] args) throws Exception {
        UserDao dao = new UserDaoMysql();

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
