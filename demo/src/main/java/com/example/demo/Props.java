package com.example.demo;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@ConfigurationProperties("prop")
@Validated
public record Props(
    @NotNull
    Integer a,
    @NotNull
    LocalDate b
) {
}
