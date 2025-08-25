package com.polynomeer.tobyspring.chap1.dao;

import com.polynomeer.tobyspring.chap1.domain.User;
import com.polynomeer.tobyspring.chap1.factory.ConnectionMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDao {

    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws Exception {
        try (Connection c = connectionMaker.makeConnection();
             PreparedStatement ps = c.prepareStatement(
                     "insert into users(id, name, password) values(?,?,?)")) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        }
    }

    public User get(String id) throws Exception {
        try (Connection c = connectionMaker.makeConnection();
             PreparedStatement ps = c.prepareStatement(
                     "select id, name, password from users where id = ?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                User u = new User();
                u.setId(rs.getString("id"));
                u.setName(rs.getString("name"));
                u.setPassword(rs.getString("password"));
                return u;
            }
        }
    }

    public void deleteAll() throws Exception {
        try (Connection c = connectionMaker.makeConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate("delete from users");
        }
    }

    public int getCount() throws Exception {
        try (Connection c = connectionMaker.makeConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("select count(*) from users")) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
