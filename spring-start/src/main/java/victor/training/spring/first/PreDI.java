package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.subp.Alta;


// marchez clasa ca Bean - sa fie detectata de Spring la startup:
// sunt scanate automat la startup cf @ComponentScan
@RestController // REST API
//@Controller // .JSP JSF VAADIN GWT Freemarker Velocity > generau HTML pe server
@RequiredArgsConstructor // ❤️ genereaza ctor cu toate campurile finale
@Slf4j // ❤️ adauga un camp slf4j Logger 'log' ca mai jos
public class PreDI {
//  private static final Logger log = LoggerFactory.getLogger(PreDI.class);

//  @Autowired // field greu de injectat un mock din @Test
//  private Alta alta;

  private final Alta alta; // HIGH COUPLING sucks
//  private final Alta2 alta1;
//  private final Alta4 alta2;
//  private final Alta6 alta3;
//  private final Alta8 alta4;
//  private final Alta4 alta5;
//  private final Alta alta6;
//  private final Alta alta7;
//  private final Alta alta8;
//  private final Alta alta9;
//  private final Alta alta0;
//  private final Alta alta12;
//  private final Alta alta44;

//  public PreDI(Alta alta) { // constructor injection
//    this.alta = alta;
//  }

  @Autowired
  public void cuAlta(Alta alta) { // method/setter injection
    log.info("Am primit alta: " + alta);
  }

  @GetMapping("prima")
  public String method() {
    alta.f();
    return "amuzant😋";
  }
}

