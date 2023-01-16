package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class IncoData {

  private final Animal dog; // surpriza: daca exista 2+ candidati la injectie pt Animal, cel numit "dog" va castiga!

  @PostConstruct
  public void method() {
    System.out.println("Oare ce am primit:  " + dog);
  }
}


interface Animal {

}
@Component
class Dog implements Animal {

}
@Component
class TomCat implements Animal {

}