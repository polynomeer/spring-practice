package com.polynomeer.tobyspring.chap1;

import com.polynomeer.tobyspring.chap1.factory.ConnectionMaker;

import java.sql.Connection;
import java.sql.Statement;

public final class Schema {
    private Schema() {
    }

    public static void createUsers(ConnectionMaker cm) throws Exception {
        try (Connection c = cm.makeConnection(); Statement st = c.createStatement()) {
            st.execute("""
                        create table if not exists users(
                          id varchar(50) primary key,
                          name varchar(100) not null,
                          password varchar(100) not null
                        )
                    """);
        }
    }
}

