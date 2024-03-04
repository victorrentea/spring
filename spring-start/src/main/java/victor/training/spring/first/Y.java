package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public class Y {
  public Y() {
    System.out.println("Y created");
  }
  @Autowired
  private MailService mailService; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate; // replace with injected Props

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
