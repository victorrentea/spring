package com.example.demo;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}

//https://chat.openai.com/
// ** Sunt un dev java. Folosesc spring boot 3.
// vreau sa scriu un aspect care sa interecepteze apelurile
// facute din com.example.demo.AController in com.example.demo.AService

// ** Dupa ce merge zi-i:
// Vreau sa interceptez pe baza de adnotare custom.