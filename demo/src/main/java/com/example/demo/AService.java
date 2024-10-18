package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;

public class AService {
  private static final Logger log = LoggerFactory.getLogger(AService.class);
  private final ARepository repository;

  public AService(ARepository repository, PlatformTransactionManager tm, AnotherClass anotherClass) {
    this.repository = repository;
    this.tm = tm;
    this.anotherClass = anotherClass;
  }

//  @Scheduled(fixedRate = 1000)
  @Scheduled(cron = "0/5 * * * * *")
  public void poll() {
    log.info("Polling...");
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
  @LoggedException
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
  public int createThrowingRuntimeException(String name)  {
    int id = repository.create(name);  // Will be registered in DB because there isn't any rollback strategy
    if (name.equals("Ex")) {
      throw new RuntimeException("Ex");
    }
    return id;
  }
  @Transactional//(rollbackFor = IOException.class)
  public int createThrowingCheckedException(String name) throws IOException {
    int id = repository.create(name);  // Will be registered in DB because there isn't any rollback strategy
    if (name.equals("Ex")) {
      throw new IOException("Ex");
    }
    return id;
  }
  //@LogExceptions

  @Transactional // (rollbackFor = Exception.class) // fix #1 for the weird behavior of COMMIT at checked exception
  // fix #2 GIVE UP on checked exceptions forever (a mistake in the Java Language). Only use unchecked exceptions.
  public void propagation() {
    try {
      bizMethod();
      bizMethod2();
    } catch (Exception e) {
//      anotherClass.saveError(e);
      TransactionTemplate tx = new TransactionTemplate(tm);
      tx.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
      tx.executeWithoutResult(status -> saveErrorCalledLocally(e));
      throw e;
    }
  }
  private final PlatformTransactionManager tm;

  public void saveErrorCalledLocally(Exception e) {
    repository.create("Error! ");
  }
  private final AnotherClass anotherClass;

  private void bizMethod() {
    repository.create("A1");
//    if (Math.random()<0.5) {
    throw new RuntimeException("Random error");
//    }
  }
  private void bizMethod2() {
    repository.create("A2");
  }
}


class AnotherClass {
  private final ARepository repository;
  AnotherClass(ARepository repository) {
    this.repository = repository;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveError(Exception e) {
    repository.create("Error! ");
  }
}