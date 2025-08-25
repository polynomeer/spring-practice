package com.polynomeer.tobyspring.chap1.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class UserDaoMysql extends UserDao {

    @Override
    protected Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/springbook?characterEncoding=UTF-8&serverTimezone=UTC",
                "spring", "book");
    }
}
