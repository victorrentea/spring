package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class A {
  private final B b;
  public String f() {
    return "Sunt aici";
  }
  @Order(20) // devine punct de cuplare global intre toate listenerele astea din toata app
  @EventListener(ApplicationStartedEvent.class) // ruleaza dupa ce TOATE beanurile au fost configurate
  public void initA() {
    System.out.println("Cellalt");
  }
//  {
//    B b= new B(new A() {
//      // fals
//    });
//    A a = new A(b);
//  }
}
@Slf4j
@Service
@RequiredArgsConstructor
class B {
  @Lazy // NU FOLOSI
  private final A a;
  // spring iti injecteaza o MINCIUNA /
  // o referinta la beanul care va sa fie... = un proxy 👻
  //  @PostConstruct // ruleaza prea devreme; dupa ce EU am fost configurat.
  @EventListener(ApplicationStartedEvent.class) // ruleaza dupa ce TOATE beanurile au fost configurate
  @Order(10)
  public void initA() {
    System.out.println("Oare ce A am primit? O minciuna? " + a.f());
  }
}