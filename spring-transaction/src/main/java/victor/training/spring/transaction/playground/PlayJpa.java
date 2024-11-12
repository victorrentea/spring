package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayJpa {
  private final MessageRepo repo;

  @Transactional
  public void writeBehind() {
    departe();
    log.info("--- End of method");
    // inserturile by default se trimit in DB fix inainte de COMMIT, aici dupa iesirea din metoda
    // De ce:
    // - poate nu mai e nevoie sa le fac deloc (lene), de ex ca sare exceptie
    // - sa le batcheuie impreuna cate 100-1000 odata
  }

  private void departe() {
    repo.save(new Message("ONE"));
    repo.save(new Message("ONE")); // PTSD - Post @Transactional Stress Disorder
    repo.flush();
    // greu de debug gafa e aici, eroarea sare mai tarziu de mai sus
  }

  public void autoSave() {
    Message entity = repo.findById(1L).orElseThrow();
    entity.setMessage("Different");
  }

  @Transactional
  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Message: " + entity);
  }
}
