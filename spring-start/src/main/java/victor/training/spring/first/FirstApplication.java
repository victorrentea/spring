package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.different.Other;

import java.lang.annotation.*;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>
// [7] @Value (+Lombok @RAC) + @ConfigurationProperties
@Retention(RetentionPolicy.RUNTIME)
@Component
@interface Mapper {}

@Import({
        X.class,
        Y.class
})
@Configuration
class XYConfig {
}

@SpringBootApplication
@ComponentScan(basePackages = "none")
@Import({
//        X.class,
//        Y.class,
        XYConfig.class,
        A.class,
        B.class,

        Other.class,
        MailServiceImpl.class
        // bad because any new class you create you need to add it to this list
        // good because it's more intentional.
        //   safe against accident like a rogue class that you hate present in a subpackage
})
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private Other other;

  @Autowired
  private A a;

  @Autowired
  private X x; // breaking news: no framework in Java cares about private.

  @Override
  public void run(String... args) throws Exception {
    System.out.println(x.logic());
  }
}



//These annotations tell spring to define a bean with this clas
// A bean is a Spring-managed instance of one of the classes. It also has a name.
// By default of a certain class there will ve a single bean = Singleton Design pattern
////@Controller // jsp is dead, long live mobile apps or angular/react

@Service
class Y {
  private final MailService mailService; // polymorphic injection

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(MailService mailService) {
    this.mailService = mailService;
  }

  public int logic() {
    mailService.sendEmail("I like 4 topics");
    return 1;
  }
}

interface MailService {
  void sendEmail(String subject);
}

@Service
@RequiredArgsConstructor
class MailServiceImpl implements MailService {
  //  private final MailSender sender; // TODO uncomment and watch it failing because it requires properties to be auto-defined

  public void sendEmail(String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@victorrentea.ro");
    message.setTo("victor@victorrentea.ro");
    message.setSubject("Training Offer");
    message.setText(body);
    System.out.println("REAL EMAIL SENDER sending email: " + message);
    //    sender.send(message);
  }
}

@Slf4j
// TODO when starting the app locally, don't send any emails, log then instead
class MailServiceLocalDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY EMAIL SENDER sending an email with subject=" + subject);
  }
}