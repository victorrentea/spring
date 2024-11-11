package com.example.demo;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;

@Slf4j
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
    log.info(this.toString());
  }
}
