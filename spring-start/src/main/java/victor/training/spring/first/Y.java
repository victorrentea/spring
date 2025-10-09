package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  private final MailService mailService; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate;

  public Y(@Qualifier("mailServiceImpl") MailService mailService) {
    this.mailService = mailService;
  }

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
