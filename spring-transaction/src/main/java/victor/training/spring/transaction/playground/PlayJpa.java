package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PlayJpa {
    private final MyEntityRepo repo;

    @Transactional
    public void writeBehind() {
        repo.save(new MyEntity("ONE").addTag("tag1"));
        log.info("--- End of method ---");
    }

    @Transactional // 😱auto-update without repo.save()
    public void autoSave() {
        MyEntity e = repo.findById(1L).orElseThrow();
        e.setName("Different");
        // repo.save(e); //traditional ≈jdbcTemplate("UPDATE...
    }









    //@GetMapping("lazy") // a) REST-called http://localhost:8080/lazy =
    @Transactional
    public void lazyLoading() { // b) !REST-called =
        MyEntity e = repo.findById(1L).orElseThrow();
        log.info("Message: {}", e.getTags()); // unde-i selectul in log?
    }

}
// TODO
//  - write behind = insert/update/delete sent to DB (=flush) after method end, before tx COMMIT
//  - flush (!= commit) also triggered: before any SELECT, repo.saveAndFlush, repo.flush()
//  - auto-save any changes to an @Entity returned by JPA within a tx
//  - lazy loading requires a) surrounding tx or b) active http request with open-session-in-view=true (default)
//  - JPA 1st level cache = findById(id) returns previous entity from memory (without SELECT)