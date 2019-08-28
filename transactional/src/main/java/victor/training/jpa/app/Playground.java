package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.sql.DataSource;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);

    @Autowired
    private DataSource ds;

    @Transactional
    public void firstTransaction() {
        JdbcTemplate jdbc = new JdbcTemplate(ds);

        jdbc.update("INSERT INTO ERROR_LOG(ID,MESSAGE) VALUES (1,'a')");
        try {
            altaMetoda();
        } catch (Exception e) {
            // ;) shaworma
        }

        log.debug("Halo!");
    }

    @Transactional // e degaba pus asta aici. Doar te confuzeaza
    public void altaMetoda() {

        if (true) throw new RuntimeException("a");
        // doar exceptiile runtime vor cauza Tranzactia **curenta** sa faca rollback
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
    }
}

//@Component
//class AltaClasa {
//
//}

//@Aspect
//    @Order(1) // runs BEFORE the TxInterceptor
//@Component
//class Test {
//    @Around("execution(* Playground.*(..))")
//    public Object intercept(ProceedingJoinPoint point) throws Throwable {
//        System.out.println("NOW");
//        return point.proceed();
//    }
//
//}