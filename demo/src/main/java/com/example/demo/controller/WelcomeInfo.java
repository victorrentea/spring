package com.example.demo.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.Map;

//@JsonIgnoreProperties("$$beanFactory")
//@Configuration
@Data
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private String greeting;
    private SupportInfo support;
    private File helpFile;
    private List<TenantServiceConfig> tenants;

    @JsonIgnore
    @Autowired
    private ApplicationContext spring;
    @PostConstruct
    public void checkData() throws ClassNotFoundException {
        System.out.println("DA MERGE");
        for (TenantServiceConfig tenant : tenants) {
            Class<?> serviceClass = Class.forName(tenant.serviceClassName);
            spring.getBean(serviceClass);
//                throw new IllegalArgumentException("No such bean class defined: " + tenant.getServiceClassName());
//            }
        }
        System.out.println("Gata verificarea");

    }



    @Data
    static class SupportInfo {
        private Map<String,List<String>> phone; //
        private String email;
    }

    @Data
    static class TenantServiceConfig {
        private String serviceClassName;
        private String country;
        private String origin;
    }
}
