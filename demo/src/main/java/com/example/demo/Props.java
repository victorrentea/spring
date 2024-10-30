package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

@ConfigurationProperties("prop")
public record Props(
    int a,
    LocalDate b
) {
}
