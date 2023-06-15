package victor.training.spring.sub;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.spring.first.MailService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ChainOfResponsibilityDesingPattern {

  // injectie de lista de interfete
  @Autowired
  public void method(Map<String, MailService> allImplementations) {
    System.out.println("Toate = " + allImplementations);
  }
}
