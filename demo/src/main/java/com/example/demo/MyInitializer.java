package com.example.demo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class MyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
   @Override
   public void initialize(ConfigurableApplicationContext applicationContext) {
      ConfigurableEnvironment environment = applicationContext.getEnvironment();
      environment.getPropertySources().addFirst(
          new MapPropertySource("wth", Map.of("name", "stuff")));
   }
}
