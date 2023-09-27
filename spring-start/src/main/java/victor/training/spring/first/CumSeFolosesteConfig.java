package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
public class CumSeFolosesteConfig {
  private final WelcomeInfo welcomeInfo;
  @GetMapping("hello")
  public /*@Size(min = 5, max = 100) @NotNull*/ String method() {
    /// intr-un colt al unei app de 100K de linii de cod (mare)
    // cineva din greseala face:
//    welcomeInfo.setWelcomeMessage("oups!"); // cum previn

    return welcomeInfo.getWelcomeMessage();
  }
}
