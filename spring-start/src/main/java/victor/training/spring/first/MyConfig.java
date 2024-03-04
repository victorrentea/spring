package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@Configuration(proxyBeanMethods = false)// will break the code again
public class MyConfig {// implements somth from Spring
  @Bean
  public X x(
      Y y // Spring will inject the Y bean here = injection point just as @Autowired field or construct params
  ) {
    // NPE because Spring did not process the @AUtowired, @Value... annotation in Y
    // because the call on the next line
    // is not seen a reference to a spring Bean,
    // but just the call of a regular method
    System.out.println("ONE");
    return new X(y);
    // NOW, the call to y() is seen as a reference to a Spring Bean
  }
  @Bean
  public Y y(MailService mailService, Props props) {
    System.out.println("Y");
    return new Y(mailService, props);
  }
  @Bean
  public MeeToo meeToo(Y y) {
    System.out.println("TWO");
    return new MeeToo(y);
  }

}
class MeeToo {
  private final Y y;

  public MeeToo(Y y) {
    this.y = y;
    System.out.println("MeeToo created");
  }
}
// in order for  line 15 not to really call :21
// spring subclasses any @Configuration class by default
// so that it can intercept the @Bean method calls