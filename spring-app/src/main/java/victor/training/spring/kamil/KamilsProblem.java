package victor.training.spring.kamil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@SpringBootApplication
@RestController
public class KamilsProblem {
  public static void main(String[] args) {
    SpringApplication.run(KamilsProblem.class, args);
  }

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("divisions")
  @CircuitBreaker(name = "divisions-circuit-breaker", fallbackMethod = "divisionsFromCache")
  @CachePut("divisions-cache") // declaratie with a proxy
  public String divisions(@RequestParam(defaultValue = "1") int i) {
    log.info("calling API + " + i);
    // if you "new" the class that you use to send HTTP/MQ, spring will NOT be able to
    // decorate it to propagate TraceId, Micrometer
    return restTemplate.getForObject("http://localhost:8888/spring-start.properties", String.class);
  }

  @Autowired
  private CacheManager cacheManager;
  public String divisionsFromCache(int i, Throwable e) {
    // programatic access to cache
    String cachedVal = cacheManager.getCache("divisions-cache").get(1, String.class);
    System.out.println("cached val  " + cachedVal + " saving the day from the error: " + e);

    return Objects.requireNonNull(cachedVal, "No data cached");
//    throw new IllegalArgumentException("No cached data for " + i);
  }
}
