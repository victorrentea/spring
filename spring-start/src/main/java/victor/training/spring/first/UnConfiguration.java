package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // scop: sa contina @Bean definitions
public class UnConfiguration {
  @Bean // programatic create a bean cu numele 'gigel'
  public PersonService gigel() {
    PersonService pathumeu = new PersonService("gigel");
    pathumeu.init();
    return pathumeu;
  }
  @Bean
  public PersonService marcel() {
    PersonService pathumeu = new PersonService("marcel");
    pathumeu.init();
    return pathumeu;
  }

}
