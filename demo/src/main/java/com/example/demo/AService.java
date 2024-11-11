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
