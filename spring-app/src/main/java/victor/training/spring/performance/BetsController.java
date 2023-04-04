package victor.training.spring.performance;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BetsController {

  // TODO report total bets -> counter
  // TODO report histogram of bets -> summary
  @Autowired
  private MeterRegistry meterRegistry;

  @GetMapping("/bet/{amount}")
  public void bet(@PathVariable int amount) {
    System.out.println("Bet " + amount);
  }



}
