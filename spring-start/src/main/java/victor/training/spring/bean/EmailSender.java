package victor.training.spring.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

// Strategy design pattern
public interface EmailSender {
  void sendEmail(String email);
}
@Component
// a spring profile is wasy
//@Profile("!local")
//@Profile("prod") // <- pitfall of profile: # I don’t always test my code, but when I do, I do it in production. YOLO
  // Profile-mania!!! WARNING: 12 profiles activate do work in prod.
// Best practice0-profiles in the ONE production I have
class ProdEmailSender implements EmailSender{ // implem1
  public void sendEmail(String email) {
     throw new IllegalArgumentException("in my local throws exception");
  }
}
@Component
@Profile("local")
@Primary // every time spring has to choose between this bean and any other for injection, this wins
  // application: src/test/java TestEmailSender.java  (aka "Fake" test double)
class LocalEnvEmailSender implements EmailSender { // implem2
  public void sendEmail(String email) {
    System.out.println(email); // in my local I want to do this
  }
}
@RequiredArgsConstructor
@Service
class SomeServiceThatNeedsUserDate {
  private final EmailSender emailSender;

  @PostConstruct
  public void method() {
    emailSender.sendEmail("this email");
  }

}