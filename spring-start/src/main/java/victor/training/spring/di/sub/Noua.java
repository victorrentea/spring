package victor.training.spring.di.sub;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Retention(RetentionPolicy.RUNTIME) // sa lase javac in .class adnotarea
@interface PotSiEu {
}

@PotSiEu
public class Noua {
  @PostConstruct
  public void method() {
    System.out.println("Buna!!");
  }
}
