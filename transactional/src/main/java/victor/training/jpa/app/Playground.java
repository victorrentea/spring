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

    @Autowired
    private AltaClasa altaClasa;

    @Transactional
    public void firstTransaction() {
        JdbcTemplate jdbc = new JdbcTemplate(ds);
        jdbc.update("INSERT INTO ERROR_LOG(ID,MESSAGE) VALUES (1,'a')");
        try {
            altaClasa.altaMetoda();
        } catch (Exception e) {
            e.printStackTrace();
            // ;) shaworma
        }
        altaMetLocala(jdbc);
        log.info("Halo!");
    }

    private void altaMetLocala(JdbcTemplate jdbc) {
        jdbc.update("INSERT INTO ERROR_LOG(ID,MESSAGE) VALUES (2,'B')");
    }
}
@Component
@Transactional
class AltaClasa {
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void altaMetoda() {
        if (true) throw new RuntimeException("a");
        // doar exceptiile runtime vor cauza Tranzactia **curenta** sa faca rollback
    }

    public void aa() {
    }
    public void bb() {
    }
    public void cc() {
    }


}

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