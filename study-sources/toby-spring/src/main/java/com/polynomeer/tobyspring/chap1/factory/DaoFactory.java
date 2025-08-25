package com.polynomeer.tobyspring.chap1.factory;

import com.polynomeer.tobyspring.chap1.dao.UserDao;

public class DaoFactory {
    // 여기서 어떤 ConnectionMaker를 쓸지 “구성”
    public ConnectionMaker connectionMaker() {
        return new MysqlConnectionMaker();
//        return new H2ConnectionMaker();
    }

    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
}
