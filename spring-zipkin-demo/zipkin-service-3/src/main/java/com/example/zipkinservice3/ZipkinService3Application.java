package com.example.zipkinservice3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableBinding(Sink.class)
@SpringBootApplication
public class ZipkinService3Application {

   public static void main(String[] args) {
      SpringApplication.run(ZipkinService3Application.class, args);
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
@MessageEndpoint
class MessageReceiver {

   @ServiceActivator(inputChannel = "input")
   public void acceptNewReservation(String message) throws InterruptedException {
      System.out.println("Message received.");
      log.info("Received message " + message);
      Thread.sleep(3000);
      log.debug("Done processing message");
   }
}

@Slf4j
@RestController
@RequiredArgsConstructor
class ZipkinController {
   private final RestTemplate rest;

   @GetMapping(value = "/service3")
   public String service3() {
      log.info("Start service 3..");
      List<String> responses4 = new ArrayList<>();
      List<Long> childrenIds = getChildren();
      for (Long childId : childrenIds) { // famous N+1 queries problem, with REST
         log.info("{}: Calling service 4", childId);
         responses4.add(rest.getForObject("http://localhost:8084/service4/{id}", String.class, childId));
         log.info("{}: Calling service 4", childId);
      }
      log.info("End service 3");
      return "3 " + String.join(" ", responses4);
   }

   private List<Long> getChildren() {
      return Arrays.asList(1L,2L,3L);
   }
}