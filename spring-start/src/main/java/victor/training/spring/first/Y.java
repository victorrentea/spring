package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Y {
//  @Qualifier("mailServiceDummy") // mentionezi numele beanului dorit daca-s 2+
//  private MailServiceDummy mailService; // direct clasa concreta
//  private MailService mailServiceDummy; // numele punctului de injectie = numele beanului ~Qualifier
  private final MailService serviceDummy; // numele punctului de injectie = numele beanului ~Qualifier
  private final Props props;
//  @Value("${props.env:ASTA}")
//  private String env;
  @Value("${database.password}") // copiat in param ctorului de lombok.config`§
  private final String dbPassword;

//  public Y(Props props,
//           MailService serviceDummy,
//           @Value("${database.password}") String dbPassword) {
//    this.props = props;
//    this.serviceDummy = serviceDummy;
//    this.dbPassword = dbPassword;
//  }

  public int logic() {
    serviceDummy.sendEmail("Go to env " + props.env());
    System.out.println("PASS: " + dbPassword);
    return 1;
  }
}
