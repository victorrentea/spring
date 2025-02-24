package victor.training.spring.first;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    X.class,
    victor.training.spring.first.config.X.class,
    Y.class,
    MailServiceImpl.class // # impl1
    /*MailServiceDummy.class*/}) // name of the bean = "mailServiceDummy"
public class MyConfig {

  // if I need to do manually "new" an object or configure it manually (eg. call init())
  @Bean // # impl2
  public MailServiceDummy dummy() { //  name of the bean = method name("dummy")
    MailServiceDummy mailServiceDummy = new MailServiceDummy(1);
    mailServiceDummy.init();
    System.out.println("I win in MyConfig");
    return mailServiceDummy;
  }

}
