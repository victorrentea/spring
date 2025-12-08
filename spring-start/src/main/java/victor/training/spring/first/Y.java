package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  @Autowired
  @Qualifier("mailServiceImpl")
  private MailService mailService; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate;

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
