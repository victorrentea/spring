package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class Playground {
    private static final Logger log = LoggerFactory.getLogger(Playground.class);
    private final JdbcTemplate jdbc;
    private final AnotherClass other;

//    @Transactional
    public void transactionOne() {
        log.info("START METHOD");

        try {
          other. doImport();
        } catch (Exception e) {
            other.persistError(e.getMessage());
            // shaworma pattern
        }
        log.info("END METHOD");
    }



   @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final JdbcTemplate jdbc;

    @Transactional
   public void doImport() throws Exception {
      jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
      //        jdbc.update("insert into MESSAGE(id, message) values ( 101,'ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2ALO2' )");
      booboo();
   }

//    @Transactional(rollbackFor = Exception.class)
    public void booboo() throws Exception {
        if (true)// throw new IOException(); // checked exceptions DO NOT cancel the current tx
            throw new NullPointerException("intentional");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistError(String message) {
        jdbc.update("insert into MESSAGE(id, message) values ( 101,'ERROR:"+message+"' )");
    }

}