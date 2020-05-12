package com.example.demo.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

//@JsonIgnoreProperties("$$beanFactory")
//@Configuration
@Data
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private String greeting;
    private SupportInfo support;

    @Data
    static class SupportInfo {
        private String phone;
        private String email;
    }
}
