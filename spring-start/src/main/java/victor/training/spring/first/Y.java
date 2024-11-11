package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequiredArgsConstructor
public class Y { // 1 instanta
  private final MailService mailService;
  private final Props props;
//  @Value("${props.gate}")
//  private final Integer gate;
//  public Y(MailService mailService,
//           @Value("${props.gate}") Integer gate) {
//    this.mailService = mailService;
//    this.gate = gate;
//  }

//  private /*static*/ int requestCount; // race bug

//  private String currentUsername; // 1 user; care user? ca pot fi 20 de req in || in app;
  // pe campurile de instanta ale beanurilor spring
  // nu tii data specifice unui request,
  // pt ca pot fi mai multe req in || la un moment dat

  // IoC
  @GetMapping // apelabila peste HTTP
  public void method() { // metoda asta poate rula pe 2,3..200 de threaduri in paralel
//    requestCount++;
  }


  public int logic() {
    mailService.sendEmail(
        "Go to gate " + props.getGate());

    return 1;
  }
}
