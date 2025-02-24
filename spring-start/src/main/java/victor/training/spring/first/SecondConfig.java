package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondConfig {
  @Bean
  public MailServiceDummy dummy() { //  name of the bean = method name("dummy")
    MailServiceDummy mailServiceDummy = new MailServiceDummy(1);
    mailServiceDummy.init();
    System.out.println("I win in SecondConfig");
    return mailServiceDummy;
  }

}
