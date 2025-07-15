package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.concurrent.CompletableFuture;

@Component
@Data
@RequestScope // exista cate 1 instanta din asta per request in curs
class RequestMetadata {
  private String currentUser;
  private String dossier;
  private String userRisk;
  private String userMarimeLaPantof;
}

@Slf4j
@RestController // 1 isntanta / app
public class RestApi {
  @Autowired
  RequestMetadata requestMetadata;

  @PostConstruct
  public void oareCeMiauInjectat() {
    System.out.println(requestMetadata.getClass());
  }

  @GetMapping("hello") // ?user=gigi
  public void method(@RequestParam String user) throws InterruptedException {
    requestMetadata.setCurrentUser(user);
    requestMetadata.setDossier("dossier");
    requestMetadata.setUserMarimeLaPantof("46");
    log.info("Intru cu " + user);
    Thread.sleep(5000);
    var future = CompletableFuture.runAsync(() -> f());
  }

  private void f() {
    log.info("Start");
    try {
      log.info(requestMetadata.getCurrentUser());
    } catch (Exception e) {
      log.error("N-o vazusem p-asta", e);
    }
    log.info("End");
  }
}
