package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

//@Component < la orice ti-e rusine sa pui altceva: eg un procesator de Apache Camel (nu e nici REST,bizlogic, repo, e doar infra💩)
//@CamelProcessor // a ta. un pis scary, non-standard

//@Controller // server-redendered HTML: thymeleaf, jsp, JSF, asp.net, vaadin;
//@RestController// in Browser (SPA): ng, react
//@Repository
@RequiredArgsConstructor
public class Y {
  private final MailService mailService;
  private Integer gate;

  public void setGate(Integer gate) {
    this.gate = gate;
  }

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
