package com.example.demo;

import org.springframework.transaction.annotation.Transactional;

public class AService {
  private final ARepository repository;

  public AService(ARepository repository) {
    this.repository = repository;
  }

  public String hi() {
    return repository.hiJooq();
  }

//  DataSource ds; // JDBC API
//    Connection connection = ds.getConnection();
//    connection.setAutoCommit(false);
//
//    connection.commit();
//    } catch (Exception) { connection.rollback();}
//
  @Transactional
  public int create(String name) {
    repository.create(name);
    if (name.equals("fail")){
      throw new RuntimeException("fail");
    }
    return repository.create(name+" Soul Mate");
  }
}
