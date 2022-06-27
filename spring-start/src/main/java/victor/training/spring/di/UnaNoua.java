package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

//@Repository // rol: interactz cu DB: SQL, PreparedStatement (doamne fereste), JdbcTemplate - nu mai e necesar cand lucrezi cu Hibernate
//@Controller // rol: trateaza apeluri HTTP si cheama logica noastra. lucra cu JSP/JSF/velocity/freemarker
//@RestController // rol trateaza servicii REST care intorc la Brow/clientni NU HTML ci JSON
//@MessageListener // asculta pe cozi. Rabbit, Kafka
//@Configuration // rol: defini de mana beanuri.

@RequiredArgsConstructor
@Slf4j
@Service
//@RequiredArgsConstructor
// numele beanului asta automat creat este "unaNoua"
public class UnaNoua implements CommandLineRunner {
//    @Autowired
//    private Alta alta;
//    @Autowired
//    private Alta2 alta2;

    private final Alta alta;
    private final Alta2 alta2;

//        @Autowired
//    public void method(Alta alta, Alta2 alta2) {
//        this.alta = alta;
//    }

    // de ce DI prin constructor?
    // 1) orice dev java intelege cand sunt atribuite dependintele (la instantiere)
    // 2) mai usor de testat.
    // 3) (probabil)❤️ e cel mai compact mod daca pui @RequiredArgsConstructor (lombok)

    @Override
    public void run(String... args) throws Exception {
        log.debug("si eu " + alta);
    }
}
@Service
class Alta {
}
@Service
class Alta2 {
}

//@Component // doar ca sa fie descoperita de class scanning
