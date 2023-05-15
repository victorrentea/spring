package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

// - Injection: field, constructor, method
// - PostConstruct, @EventListener, CommandLineRunner
// - Qualifier
// - Primary
// - Profile
// - inject List<BeanInterface>
// - ApplicationContext#getBean
// [7] @Value (+Lombok @RAC) + @ConfigurationProperties

@SpringBootApplication
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

