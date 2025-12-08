package victor.training.spring.first;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Y {
  private final MailService mailService; // polymorphic injection
  private final ThreadPoolTaskExecutor executor2;
  @Value("${props.gate}") // merge datorita lombok.config
  private final Integer gate;


  public int logic() {
    mailService.sendEmail("Go to gate " + gate);
    Entity rupiInTeste = new Entity()
        .setName("John")
        .setPhone("8989989");
    return 1;
  }
}
@Data
class Entity {
  private String name,phone;
}

