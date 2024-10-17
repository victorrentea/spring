package com.example.demo;

import java.io.IOException;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
  //@Transactional
  @LogExceptions
  // because the method cannot be overridden in the proxy
  // generated super class, so when the controller causes this method,
  // the method tries to use a field,
  // which was not dependency injected by (Spring)
  public final int create(String name) {

    repository.create(name);
    if (name.equals("65fail")){
      justForHijack();
      throw new RuntimeException("fail");
    }
    return repository.create(name+" Soul Mate");
  }

  @LogExceptions
  public static void justForHijack(){
    throw new RuntimeException("Hi, jack :) ");
  }
}
