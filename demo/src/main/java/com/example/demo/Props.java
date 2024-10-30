package com.example.demo;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Slf4j
@ConfigurationProperties("prop")
@Validated
public record Props(
    @NotNull
    Integer a,
    @NotNull
    LocalDate b
) {

  @EventListener(ApplicationReadyEvent.class)
  public void printProps() {
    log.info(toString());
  }
}
