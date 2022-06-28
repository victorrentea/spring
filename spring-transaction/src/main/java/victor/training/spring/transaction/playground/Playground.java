package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        System.out.println("😁");

        System.out.println(repo.findAllByMessage("ALO"));
        try {
            other.bizLogicRiscant();
        } catch (Exception e) {
            other.persistError(e);
        }
    }

    @Transactional
    // nu are sens daca doar citesti date. ( in afara cazului cand te bazezi pe lazy load - mare greseala )
    // nu are sens daca inserezi un singur obiect (repo.save are @Transactional inauntrul lui anyway)
    public void transactionTwo() {
        Message message = repo.findById(100L).orElseThrow();

        // ANTIPATTERN: a chema REST/SOAP/RMI in @Transactional method

//        new RestTemplate().getForObject("url", CevaDto.class); // PERICULOS cand ai un use-case hot  care cheama alte API-uri!!
// apel de retea ce dureaza ~10-20 ms in testele tale; pana-ntro zi, cand in prod raspunde 10 SECUNDE.

        // inchipuie-ti 20 de apeluri simultane la acest endpoint toate stand sa asptept dupa cele 10 sec.
        // app ta este KO : te-a invins. Orice acces vrei sa faci in baza nu mai e posibil
            // Pentru ca toate cele 20 de connections din POOL sunt blocate. >> "Timeout waiting for connection"

        repo.save(new Message("ceva nou"));
        repo.save(new Message("ceva nou"));
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;

    @Transactional // atomic , dar vine si proxy aici.
    public void bizLogicRiscant() {
        repo.save(new Message("INSERT tranzactia"));
        repo.save(new Message("UPDATE count.amount -- "));
        throw new RuntimeException("BUM");
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void persistError(Exception e) {
        e.printStackTrace();
        Message errorToInsert = new Message("ERROR: " + e.getMessage());
        repo.save(errorToInsert); // aici scriu intr-o tranzactie deja moarta (zombie)
    }
}