package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class YMe {
  private final MailService mailService; // polymorphic injection
//  private final X x;
  YMe(MailService mailService) {
    this.mailService = mailService;
    System.out.println("Y created");
  }

  @Value("${props.gate}")
  private Integer gate; // replace with injected Props

  @PostConstruct
  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
