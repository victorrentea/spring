package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import victor.training.spring.supb.AnotherBean;
import victor.training.spring.supb.SomeBean;
import victor.training.spring.supb.X;

// - Dependency Injection: field, constructor, method
// - Defining beans: @Component & co, @ComponentScan
// - Cyclic dependencies
// - Startup code: PostConstruct, @EventListener, CommandLineRunner
// - ApplicationContext#getBean
// - Qualifier, bean names
// - Primary
// - Profile (bean & props)


// - @Autowired List<BeanInterface>
// - @Scope
// - @Configuration @Bean proxyMethods

// - @Value(${}) + @ConfigurationProperties
@SpringBootApplication
@Import({
        X.class,
        SomeBean.class,
        AnotherBean.class
})
//@ComponentScan(basePackages = {"victor.training.spring.supb", "victor.training.spring.first"})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);

//    new SpringApplicationBuilder()
//            .sources(FirstApplication.class)
//            .listeners(
//                    new ApplicationListener<ApplicationEnvironmentPreparedEvent>() {
//                      @Override
//                      public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
//                        ConfigurableEnvironment environment = event.getEnvironment();
//
//                        PropertySource<?> propertySource = PropertySource.named("myProps")
//                                .withProperty("john.name", "John Doe"));
//                        environment.getPropertySources().addFirst(propertySource);
//                      }
//                    })
//            .run(args);
  }

  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }

//  @Bean // last resort: manually construct a bean. Seek first some propertyto toggle to change Spring behavhavior
//  MailSender mailSender(MailProperties properties) {
//    return new MailSender() {
//      @Override
//      public void send(SimpleMailMessage simpleMessage) throws MailException {
//
//      }
//
//      @Override
//      public void send(SimpleMailMessage... simpleMessages) throws MailException {
//
//      }
//    };
//  }
}

