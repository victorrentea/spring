package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AService {
  private final Config config;
  public String m() {
    return "hello! " + config.x();
  }
}

//https://chat.openai.com/
// ** Sunt un dev java. Folosesc spring boot 3.
// vreau sa scriu un aspect care sa interecepteze apelurile
// facute din com.example.demo.AController in com.example.demo.AService

// ** Dupa ce merge zi-i:
// Vreau sa interceptez pe baza de adnotare custom.