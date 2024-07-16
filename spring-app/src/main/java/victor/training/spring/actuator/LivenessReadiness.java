package victor.training.spring.actuator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LivenessReadiness {
  private final ApplicationEventPublisher eventPublisher;
  @GetMapping("api/kill")
  public String kill() {
    AvailabilityChangeEvent.publish(eventPublisher, "api-call", LivenessState.BROKEN);
    return "Set app liveness to DOWN: http://localhost:8080/actuator/health/liveness";
  }
  @GetMapping("api/unavailable")
  public String unavailable() {
    AvailabilityChangeEvent.publish(eventPublisher, "api-call", ReadinessState.REFUSING_TRAFFIC);
    return "Set app to refuse traffic: http://localhost:8080/actuator/health/readiness";
  }
  @GetMapping("api/available")
  public String available() {
    AvailabilityChangeEvent.publish(eventPublisher, "api-call", ReadinessState.ACCEPTING_TRAFFIC);
    return "Set app to accept traffic: see at http://localhost:8080/actuator/health/readiness";
  }
}
