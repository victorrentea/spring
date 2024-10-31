package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class AltConfig {
//  @Bean
  public DataSource dataSource() {
    return new AbstractDataSource() {
      @Override
      public Connection getConnection() throws SQLException {
        return null;
      }

      @Override
      public Connection getConnection(String username, String password) throws SQLException {
        return null;
      }
    };
  }
}
