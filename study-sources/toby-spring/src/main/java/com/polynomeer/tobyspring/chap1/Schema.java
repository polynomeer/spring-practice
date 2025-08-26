package com.polynomeer.tobyspring.chap1;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DDL 유틸: DataSource 기반으로 테이블 생성
 */
public final class Schema {
    private Schema() {
    }

    public static void createUsers(DataSource dataSource) throws SQLException {
        try (Connection c = dataSource.getConnection();
             Statement st = c.createStatement()) {
            st.execute("""
                        CREATE TABLE IF NOT EXISTS users(
                          id        VARCHAR(50)  PRIMARY KEY,
                          name      VARCHAR(100) NOT NULL,
                          password  VARCHAR(100) NOT NULL
                        )
                    """);
        }
    }
}

