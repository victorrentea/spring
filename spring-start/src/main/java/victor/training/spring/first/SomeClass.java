package victor.training.spring.first;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SomeClass implements CommandLineRunner {
  @Override
  public void run(String... args) throws Exception {
     // useful for batch jobs
  }
}
