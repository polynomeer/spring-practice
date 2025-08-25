package com.polynomeer.tobyspring.chap1.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/springbook?characterEncoding=UTF-8&serverTimezone=UTC",
                "spring", "book"
        );
    }
}
