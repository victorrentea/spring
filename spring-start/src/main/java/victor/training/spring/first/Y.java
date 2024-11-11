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
public class Y { // 1 instanta
  @Autowired
  private MailService mailService; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate; // replace with injected Props

  Y() {
    // spring iti instantiaza o singura data beanurile (clasele marcate cu @Component & friends)
    // = SINGLETON.
    // stiind ca exista o singura instanta, ce nu ar trebui sa tii in campurile unui bean
    System.out.println("new instance");
  }

//  private /*static*/ int requestCount; // race bug

//  private String currentUsername; // 1 user; care user? ca pot fi 20 de req in || in app;
  // pe campurile de instanta ale beanurilor spring
  // nu tii data specifice unui request,
  // pt ca pot fi mai multe req in || la un moment dat

  @GetMapping // apelabila peste HTTP
  public void method() { // metoda asta poate rula pe 2,3..200 de threaduri in paralel
//    requestCount++;
  }


  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
