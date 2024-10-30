package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
  @Log
@RequiredArgsConstructor
public class AService {
  public String f() {
    return "Hi! ";
  }
}
