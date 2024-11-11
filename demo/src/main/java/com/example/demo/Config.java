package com.example.demo;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "config")
public record Config(
    @NotNull
    Integer x,
    @NotNull
    Integer y
) {
  @EventListener(ApplicationStartedEvent.class)
  public void print() {
    System.out.println(this);
  }
}
