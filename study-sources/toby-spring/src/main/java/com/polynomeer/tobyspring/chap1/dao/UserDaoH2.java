package com.polynomeer.tobyspring.chap1.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class UserDaoH2 extends UserDao {

    @Override
    protected Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
    }
}
