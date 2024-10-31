package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "features")
public record Features(Set<String> flags) {
  enum Feat {
    YOXO_CORPORATE_SHARDING,
    YOXO_PERSONAL_DEBIT
  }
}
