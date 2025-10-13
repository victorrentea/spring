package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  @Autowired
//  @Qualifier("mailServiceDummy") // mentionezi numele beanului dorit daca-s 2+
//  private MailServiceDummy mailService; // direct clasa concreta
//  private MailService mailServiceDummy; // numele punctului de injectie = numele beanului ~Qualifier
  private MailService serviceDummy; // numele punctului de injectie = numele beanului ~Qualifier
  @Value("${props.gate}")
  private Integer gate;

  public int logic() {
    serviceDummy.sendEmail("Go to gate " + gate);

    return 1;
  }
}
