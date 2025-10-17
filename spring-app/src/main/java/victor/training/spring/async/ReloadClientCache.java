package victor.training.spring.async;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReloadClientCache {

//  @Scheduled(cron = "0 */5 * * * *")
//  @Scheduled( fixedRateString = "1000") //spring drives

  // @PutMapping =REST api called via a curl command by cron job in OS
  public void pullsAllClientData() {
    System.out.println("Downloading to refresh my cache");
  }
}
