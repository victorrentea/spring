package victor.training.spring.first.alt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.Y;

// Spring promoveaza separatia intre layere tehnice
// la startup spring scaneaza dupa astfel de clase toate pachetele
  // de sub pachetul in care ai pus @SpringBootApplication
  // = ComponentScan
//@RestController // expune API REST: @GetMapping, @PostMapping
//@Controller //X  generezi HTML de pe server: .jsp✅ .jsf VAADIN✅ Velocity Freemarker
//@Service // business logic
//@Repository // clasa persista date intr-o DB (SQL, Mongo,...)

@Slf4j // genreaza un private static final Logger log = LoggerFactory.getLogger(X.class);
@Component // pe orice altceva (chestii tehnice)
@RequiredArgsConstructor // ii spune lui lombok sa genreze ctor pt toate campurile finale
public class X {

  // TODO Dependency Injection:
// // Springule, cauta prin componentele existente una compatibila si daca gaseste,
//  // seteaza acest camp la o referinta la acel obiect.
//  // Spring/Hibernate nu au nici o problema cu a scrie/citi din campuri private
//  @Autowired
//  private Y y; // injection point
  // field injection e descurajat pt ca
  // 1. un camp privat nu poate fi setat manual din @Teste = Mockito.mock(Y.class);
  //      => @InjectMocks
  //      => reflection din teste ReflectionUtils.setField("y", mock(Y.class));
  // 2. promoveaza campuri ne-finale

  private final Y y; // constructor-injection

//  public X(Y y) { // "injection point" este contructor param
//    this.y = y;
//  }// din teste new X(mock(Y.class))

  // method injection: folositi cand nu vrei doar sa stochezi dependinta,
  // ci sa EXECUTI ceva cu ea la startup
  @Autowired
  public void orice(Y y2, Y y3) {
    System.out.println("Chemata la startup de Spring cu dep dorite " + y2);
  }

  public int logic() {
    return 1 + y.logic();
  }
}
