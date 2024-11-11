package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Logged
@RequiredArgsConstructor
public class AService { // subclasat de proxy
  private final Config config;
  public String m() {
//    if(true) throw new RuntimeException("Intentionat sa vezi proxyul in fata meodei asteia in call stack");
    return "hello! " + config.x();
  }
}

//https://chat.openai.com/
// ** Sunt un dev java. Folosesc spring boot 3.
// vreau sa scriu un aspect care sa interecepteze apelurile
// facute din com.example.demo.AController in com.example.demo.AService

// ** Dupa ce merge zi-i:
// Vreau sa interceptez pe baza de adnotare custom.