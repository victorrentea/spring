package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import victor.training.spring.sub.X;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct & CommandLineRunner & EventListener
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>
// [7] @Value (+Lombok @RAC) + @ConfigurationProperties

@SpringBootApplication
@ComponentScan({"victor.training.spring.first", "victor.training.spring.sub"})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @Override
  public void run(String... args) throws Exception {
    System.out.println(x.logic());
  }
}

