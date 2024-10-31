package victor.training.spring.actuator;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class StartupProbeController {
  private final AtomicBoolean appStarted = new AtomicBoolean(false);

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    appStarted.set(true);
  }

  @GetMapping("/started")
  public ResponseEntity<String> startup() {
    return appStarted.get() ?
        ResponseEntity.ok("Application started") : // 200 status
        ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE) // 503
            .body("Not ready");
  }
}