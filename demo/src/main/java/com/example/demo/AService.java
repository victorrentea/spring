package com.example.demo;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Logged
@Slf4j
@RequiredArgsConstructor
public class AService { // subclasat de proxy
  private final Config config;
  @Timed
  public String metodaSmechera() {
    //if(true) throw new RuntimeException("Intentionat sa vezi proxyul in fata meodei asteia in call stack");
    log.trace("debug pt o problema nereproductibila pe local, ci doar in productie "+config);
    return "hello! " + config.x();
  }

  @Scheduled(fixedRateString = "${rate.millis}")
//  @Async // EVITA
//  @Scheduled(cron = "${cron.expression}")
  public void salut() throws InterruptedException {
    log.info("Start");
    Thread.sleep(2000); // nu e re-entrant decat daca @Async (=o cauti)
    log.info("End");
  }


}
