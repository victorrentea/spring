package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  @Autowired
  private MailService mailService; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate; // replace with injected Props

  Y() {
    // spring iti instantiaza o singura data beanurile (clasele marcate cu @Component & friends)
    // = SINGLETON
    System.out.println("new instance");
  }

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
