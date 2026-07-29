package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Component < la orice ti-e rusine sa pui altceva: eg un procesator de Apache Camel (nu e nici REST,bizlogic, repo, e doar infra💩)
//@CamelProcessor // a ta. un pis scary, non-standard

//@Controller // server-redendered HTML: thymeleaf, jsp, JSF, asp.net, vaadin;
//@RestController// in Browser (SPA): ng, react
//@Repository
@Service
@RequiredArgsConstructor
public class Y {
//  @Qualifier("mailServiceDummy") //1
//  private final MailServiceDummy mailService; // 2
  private final MailService mailServiceImpl;
//  @Value("${props.gate}")
//  private Integer gate;
  private final Props props;

  public int logic() {
    mailServiceImpl.sendEmail("Go to gate " + props.gate());

    return 1;
  }
}
