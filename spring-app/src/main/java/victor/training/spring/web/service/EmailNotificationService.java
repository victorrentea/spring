package victor.training.spring.web.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component // chestii
@Service // biz logic
public class EmailNotificationService implements NotificationService {
  @Override
  public void notify(String message) {
    System.out.println("Email: "+ message);
  }
}
