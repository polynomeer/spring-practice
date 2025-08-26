package com.polynomeer.tobyspring.chap1.factory;

import com.polynomeer.tobyspring.chap1.dao.UserDao;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public DataSource dataSource() {
        // H2 in-memory (테스트용). 필요 시 MySQL 등으로 교체 가능.
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:chap15;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        ds.setUser("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public UserDao userDao(DataSource dataSource) {
        // 스프링 컨테이너가 dataSource 빈을 찾아 주입
        return new UserDao(dataSource);
    }
}
