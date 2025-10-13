package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.web.controller.dto.TrainingDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TracingDemo {
  private final RestTemplate restTemplate;
  // intr-o alta aplicatie... (microserviciu)
  @GetMapping("tracing")
  public TrainingDto[] method() {
    log.info("(1)Calling...");
    TrainingDto[] array = restTemplate
        .getForEntity(
            "http://localhost:8080/api/trainings",
            TrainingDto[].class)
        .getBody();
    return array;
  }
}
