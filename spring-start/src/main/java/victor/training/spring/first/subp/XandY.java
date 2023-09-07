package victor.training.spring.first.subp;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

//@Entity NO - hibernate manages it, not Spring

//@Configuration // contain programatic @Bean definition
//
//@Repository // DB access
//@Controller // redirects, .jsp/.jsf server-side rendered HTML (not used anymore)
//@RestController // REST API
//@Service // contains BIZ LOGIC
//@Mapper // my own to express the semantic of a "mapper" component

@Slf4j
@RequiredArgsConstructor // pragmatic ctor injection
@Service // infra/technicalities
public class XandY {
  // there is a SINGLE instance of X created by Spring
  // named "xandY" (small first letter)
  // ==>  needs to be STATE LESS (no request/user data in fields)
//  private String currentUser; // illegal
//  private String tenantId;

  private final Y y; // immutable + testing + forces the caller to HAVE a Y with Java language constructs

  //  @Autowired
//  private Y y; // #2 field injection
//  public XandY(Y y) { // #1 ❤️constructor
//    this.y = y;
//  }

  // #3 method injection (rarely used)
//  @Autowired
//  public void init(Y y) {
//    // only use if you DO something with y, not just store it in a field
//    this.y2 = y;
//  }


//  @Autowired
//  ApplicationContext spring;

  public int logic() {
//    Y y = spring.getBean(Y.class); // dangerous: at runtime Y might not be found
    // => crash at runtime, not at startup as DI

    return 1 + y.logic();
  }

  // Struts, EJB, VAADIN not integrated with Spring. From them you wanted to delegate control to SPring
  // ApplicationContext.getBean




//  static {
//    new XandY()
//  }
}

//@Slf4j
//interface No {
//}

//@Slf4j
//@RequiredArgsConstructor
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@Retention(RUNTIME) // stops javac from removing it at compilation
//@interface Fun {
//
//}


@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@Fun
class A {
  @Lazy // propagated thanks to lombok.config
  B b;
  @Value("${mail.sender}")
  String mailSender;

  // annotations on injection point
//  A(@Lazy B b,
//    @Value("${mail.sender}") String mailSender) { //dark magic,
//    // allows spring to use a proxy to be able to instantiate both
//    this.b = b;
//    this.mailSender = mailSender;
//  }
}
@Component
class B {
  private final A a;

  B(A a) {
    this.a = a;
  }
}

//class HardCore {
//  public void method() {
//    B bProxy = new B() {
//       // any call to a method will dispatched to b created 2 lines below
//    };
//    A a = new A(bProxy);
//    B b = new B(a);
//  }
//}