package victor.training.spring.kamil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@RestController
public class KamilsProblem {
  public static void main(String[] args) {
    SpringApplication.run(KamilsProblem.class, args);
  }

  @GetMapping("divisions")
  @CircuitBreaker(name = "divisions-circuit-breaker", fallbackMethod = "divisionsFromCache")
  @CachePut("divisions-cache")
  public String divisions() {
    log.info("calling API");
    return new RestTemplate().getForObject("http://localhost:8888/spring-start.properties", String.class);
  }

  @Cacheable("divisions-cache")
  public String divisionsFromCache() {
    throw new IllegalArgumentException("No cached data!");
  }

}
