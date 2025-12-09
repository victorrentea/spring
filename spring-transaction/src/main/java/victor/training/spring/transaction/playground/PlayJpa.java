package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PlayJpa /*extends BaseService  @Transactional{}*/ {
    private final MyEntityRepo repo;

    public void writeBehind() {
        anafApiCall();
        repo.save(new MyEntity("ONE").addTag("tag1"));
        log.info("--- End of method ---");
    }

    @Transactional // 😱 auto-update without repo.save()
    public void autoSave() {
        MyEntity e = repo.findById(1L).orElseThrow(); // 1 SELECT
        e.setName("Different");
        altaMetoda();
        // repo.save(e); //traditional ≈jdbcTemplate("UPDATE...
    }

    private void altaMetoda() {
        System.out.println(repo.findById(1L).orElseThrow()); // 0 SELECT (1st level cache)
            // inseamna ca JPA a tinut undeva intr-un cache Map<pk,entity> in mem entitatea resp :22
        System.out.println(repo.findById(1L).orElseThrow());
        System.out.println(repo.findById(1L).orElseThrow());

        var e1 = repo.findById(1L).orElseThrow();
        e1.setName("Changed Again");
        var e2 = repo.findById(1L).orElseThrow();
        System.out.println("e1 == e2 ? " + (e1 == e2));
        MyEntity e3 = repo.findByName("Changed Again").get(0);
        System.out.println("e1 == e3 ? " + (e1 == e3));
    }



    // 10 caluri paralele pe api-ul asta = nici un alt endpoint lovi baza
    // /actuator/health = mai e app in viata?
    // majoritatea app includ accesul in DB in health-check
    //  daca raspunzi cu 500/timeout x 3 ori => k8s/GKE iti da kill+restart
    @GetMapping("lazy") // a) REST-called http://localhost:8080/lazy =
    @Transactional
    public void lazyLoading() { // b) !REST-called =
        MyEntity e = repo.findById(1L).orElseThrow();
        log.info("Message: {}", e.getTags()); // unde-i selectul in log?
        anafApiCall(); // ❌ SA NU FACI API CALLS CU TRANZACTIE DESCHISA!
    }
    @SneakyThrows
    private /*synchronized*/ void anafApiCall() {
        Thread.sleep(5_000); // tine 1/10 JDBC conn ocupata pe durata API-calluluil
    }

}
// TODO
//  - write behind = insert/update/delete sent to DB (=flush) after method end, before tx COMMIT
//  - flush (!= commit) also triggered: before any SELECT, repo.saveAndFlush, repo.flush()
//  - auto-save any changes to an @Entity returned by JPA within a tx
//  - lazy loading requires a) surrounding tx or b) active http request with open-session-in-view=true (default)
//  - JPA 1st level cache = findById(id) returns previous entity from memory (without SELECT)