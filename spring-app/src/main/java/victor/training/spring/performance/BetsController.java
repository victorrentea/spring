package victor.training.spring.performance;

import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BetsController {

  @Autowired
  private JackpotRepo jackpotRepo;

  // TODO report total bets -> counter
  // TODO report histogram of bets -> summary
  @Autowired
  private MeterRegistry micrometer;


//  @Retry()
  @GetMapping("/bet/{amount}")
  @Timed("bet.time") // or registry.timer()
  public String bet(@PathVariable int amount) {
    System.out.println("Bet " + amount);
    jackpotRepo.addJackpot((int) (0.1f * amount));
    micrometer.counter("bets").increment(amount);
    micrometer.summary("bets.histo").record(amount);
    return "🍀";
  }

  @EventListener(ApplicationStartedEvent.class)
  public void initGauge() {
    micrometer.gauge("jackpot", jackpotRepo, JackpotRepo::getJackpot);
  }


}

@Repository
class JackpotRepo {
  //
  int jackpot;

  public int getJackpot() {
    return jackpot;
  }

  public void addJackpot(int amount) {
    jackpot += amount;
  }
}