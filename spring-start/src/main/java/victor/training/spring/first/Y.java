package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class Y {
  private final MailService mailService; // polymorphic injection
  private final Props props;
  @Lazy // injecteaza un proxy; X real e cerut din context abia la primul apel
  private final X x;

  @PostConstruct
  public void method() {
    System.out.println("Oare ce instanta de X am primit?" + x.getClass());
  }





  
//  @Value("${props.gate}")
//  private final Integer gate;

  // acum lombok genereaza EXACT asta:
//  public Y(MailService mailService, @Value("${props.gate}") Integer gate) {
//    this.mailService = mailService;
//    this.gate = gate;
//  }

  public int logic() {
    mailService.sendEmail("Go to gate " + props.gate());

    return 1;
  }
}
