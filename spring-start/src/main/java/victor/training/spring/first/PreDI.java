package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.first.subp.Alta;


// marchez clasa ca Bean - sa fie detectata de Spring la startup:
// sunt scanate automat la startup cf @ComponentScan
@RestController // REST API
//@Controller // .JSP JSF VAADIN GWT Freemarker Velocity > generau HTML pe server
public class PreDI {
  @Autowired // Springule, injecteaza-mi aici o instanta d-aia
  private Alta alta;
  @GetMapping("prima")
  public String method() {
    alta.f();
    return "amuzant😋";
  }
}

