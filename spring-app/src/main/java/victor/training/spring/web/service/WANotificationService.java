package victor.training.spring.web.service;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

//@Component // chestii
@Service // biz logic
@Primary
@Profile("wa")
public class WANotificationService implements NotificationService {
  @Override
  public void notify(String message) {
    System.out.println("WA: "+ message);
  }
}
