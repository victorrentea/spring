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
@RequiredArgsConstructor // Lombok e un virus de Javac care adauga cod pe care tu nu-l scrii
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }


  // DI: Springule, injecteaza aici un o instanta dupa tip din container
//  @Autowired
//  private X x; // 1 istoric: field injection

  private final X x; // 2: ⭐️ constructor injection recomandat
  private final X x2; //
//  public FirstApplication(X x) {
//    this.x = x;
//  }


//  @Autowired // 3 setter injection
//  public void setZ(Z z) {
//    this.z = z;
//  }

  @Override // from CommandLineRunner, chemata de spring cu param de command line
  public void run(String... args) {
    System.out.println(x.logic());
  }
}

