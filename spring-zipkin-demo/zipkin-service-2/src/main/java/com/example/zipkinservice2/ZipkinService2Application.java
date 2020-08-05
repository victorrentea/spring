package com.example.zipkinservice2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ZipkinService2Application {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinService2Application.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Sampler alwaysSampler() {
        return new AlwaysSampler();
    }
}

@Slf4j
@RestController
@RequiredArgsConstructor
class ZipkinController {
    private final RestTemplate restTemplate;

    @GetMapping(value = "/service2")
    public String service2() throws InterruptedException {
        log.info("Start service 2...");

        log.info("Now let's create some intentional delay to emulate working with Files, DB query, CPU task...");
        Thread.sleep(5 * 1000);
        log.info("Long job done...");

        log.info("Calling service3");
        String response = restTemplate.getForObject("http://localhost:8083/service3", String.class);
        log.info("Got response from service3");
        return "2 " + response;
    }
}