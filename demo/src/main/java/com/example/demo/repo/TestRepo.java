package com.example.demo.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class TestRepo {
    private final JdbcTemplate jdbc;
//    RestTemplate


    public TestRepo(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public String test() {
        return jdbc.queryForObject("SELECT count(*) FROM users", String.class);
    }
}
