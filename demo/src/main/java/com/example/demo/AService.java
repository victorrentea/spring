package com.example.demo;

import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

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
    return repository.create(name+" Soul Mate");
  }

  @Transactional
  public int createAtomic(String name) {
    repository.create(name);
    return repository.create(name);
  }

  @Transactional(readOnly = true)
  public int createReadOnly(String name) {  // This is a logical fallacy, create/read-only :D
    return repository.create(name); // For some reason this is commited when it should not be, will investigate
  }

  @Transactional
  public int createWithException(String name) throws Exception {
    repository.create(name);  // Will be registered in DB because there isn't any rollback strategy
    if (name.equals("Ex")) {
      throw new Exception("Ex");
    }
    return repository.create(name);
  }

  //@LogExceptions
  @Transactional(rollbackFor = IOException.class)
  public int createWithIOException(String name) throws IOException {
    repository.create(name);  // This will not be registered in DB, it will be rolled back
    if (name.equals("Ex")) {
      throw new IOException("Ex");
    }
    return repository.create(name);
  }
}
