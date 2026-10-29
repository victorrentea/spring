package sample.shop;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/** A plain-JDBC app outside victor.training.sequence: what a user of the module looks like. */
@SpringBootApplication
@EnableAsync
public class ShopApp {
}

@Service
class OrderService {
  private final JdbcTemplate jdbc;
  private final AuditService audit;

  OrderService(JdbcTemplate jdbc, AuditService audit) {
    this.jdbc = jdbc;
    this.audit = audit;
  }

  @Transactional
  public void place(String item) {
    jdbc.update("insert into orders(item) values (?)", item);
    audit.log("placed " + item);
  }

  @Transactional
  public void placeAndShip(String item) {
    jdbc.update("insert into orders(item) values (?)", item);
    CompletableFuture.runAsync(() -> audit.log("shipped " + item)).join();
  }

  @Transactional
  public void placeAndNotify(String item) {
    jdbc.update("insert into orders(item) values (?)", item);
    audit.logLater("notified " + item); // returns at once: the caller does not wait for it
  }

  @Transactional
  public void placeAndFail(String item) {
    jdbc.update("insert into orders(item) values (?)", item);
    throw new IllegalStateException("out of stock");
  }

  @Transactional
  public void swallowJoinedFailure(String item) {
    jdbc.update("insert into orders(item) values (?)", item);
    try {
      audit.failInsideCallerTransaction();
    } catch (IllegalStateException e) {
      // too late: the proxy of failInsideCallerTransaction() already doomed this transaction
    }
  }
}

@Service
class AuditService {
  private final JdbcTemplate jdbc;

  AuditService(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Transactional(propagation = REQUIRES_NEW)
  public void log(String message) {
    jdbc.update("insert into audit_log(message) values (?)", message);
  }

  @Async
  @Transactional
  public void logLater(String message) {
    jdbc.update("insert into audit_log(message) values (?)", message);
  }

  @Transactional
  public void failInsideCallerTransaction() {
    throw new IllegalStateException("audit down");
  }
}
