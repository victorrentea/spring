package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RestApi {
  private String currentUser;

  @GetMapping("hello") // ?user=gigi
  public void method(@RequestParam String user) throws InterruptedException {
    this.currentUser=user;
    log.info("Intru cu " + user);
    Thread.sleep(5000);
    f();
  }

  private void f() {
    log.info(currentUser);
  }
}
