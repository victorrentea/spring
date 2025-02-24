package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  @Autowired
//  @Qualifier("dummy") // bean name
  private MailService dummy; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate;

  public int logic() {
    dummy.sendEmail("Go to gate " + gate);

    return 1;
  }
}
