package com.example.zipkinservice1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ZipkinService1Application {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinService1Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // traces 100% of the requests. In production consider PercentageBasedSampler to limit performance impact
    @Bean
    public Sampler alwaysSampler() {
        return new AlwaysSampler();
    }
}

@Slf4j
@RestController
@RequiredArgsConstructor
class ZipkinController {
    private final RestTemplate rest;

    @GetMapping({"service1","/"})
    public String service1() {
        log.info("Start service 1..");
        String response = rest.getForObject("http://localhost:8082/service2", String.class);
        log.info("End service 1..");
        return "Hi... 1 " + response;
    }

}
