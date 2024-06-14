package victor.training.spring.first;

import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j

@RestController
public class DemoApplication {

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
  }

  record Dto(@Size(min = 10) String firstName, String lastName) {
  }

  @PostMapping("/negru")
  public void negru(@Validated @RequestBody Dto dto) {
    log.info("Apa {}", dto);
  }


  @GetMapping
  public String hi() {
    if (Math.random() < 0.5) throw new RuntimeException("💥");
    return "⛱️";
  }
//	@GetMapping
//	public ResponseEntity<String> hi() {
//		try {
//			if (Math.random() < 0.5) throw new RuntimeException("💥");
//			return ResponseEntity.ok("⛱️");
//		} catch(Exception e) {
//			return ResponseEntity.status(400)
//					.header("X-Oups","true")
//					.build();
//		}
//	}
}
