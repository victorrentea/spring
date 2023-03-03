package victor.training.spring.jooq;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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

  public String wsdlCall(String data) {
    return Lib.blockingCall(data);
  }
}