package victor.training.spring.jooq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClassicDependencies {
  public String mongoRepoSave(String document) {
    log.info("Persist the document: {}", document);
    return document;
  }

  public void rabbitSend(String message) {
    log.info("🐇 send message: {}", message);
  }

  public ReaderProfile fetchUserProfile(Long readerId) {
    log.info("Rest call PROFILE: {}", readerId);
    return new ReaderProfile();
  }
  public boolean checkAddress(ReaderProfile readerProfile) {
    log.info("Rest call ADDRESS: {}", readerProfile);
    return true;
  }
  public boolean checkPhone(ReaderProfile readerProfile) {
    log.info("Rest call PHONE: {}", readerProfile);
    return true;
  }
}
