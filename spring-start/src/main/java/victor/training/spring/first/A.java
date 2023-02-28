package victor.training.spring.first;

import org.springframework.stereotype.Component;

@Component
public class A {
  private final B b;

  public A(B b) {
    this.b = b;
  }
}

@Component
class B {
  private final A a;

  B(A a) {
    this.a = a;
  }
}