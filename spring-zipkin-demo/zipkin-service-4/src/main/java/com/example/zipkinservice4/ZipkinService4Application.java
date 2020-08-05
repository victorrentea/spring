package com.example.zipkinservice4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@EnableBinding(Source.class)
@SpringBootApplication
public class ZipkinService4Application {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinService4Application.class, args);
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
    private final Source channel;

    @GetMapping(value = "/service4")
    public String service4() {
        log.info("Inside zipkinService 4..");
        log.info("Sending Message to Service 3");
        Message<String> message = MessageBuilder.withPayload("Hi From Service4!").build();
        channel.output().send(message);
        log.info("Message Sent");
        return "4";
    }
}