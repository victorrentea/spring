package com.example.reservationclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EnableBinding(Source.class)
@EnableHystrix
@EnableCircuitBreaker
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient

public class ReservationClientApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}

}

@RestController
class ReservationClientResource {
	@Autowired
	private RestTemplate restTemplate;

	public List<String> noop() {
		System.out.println("A sarit siguranta");
		return Collections.emptyList();
	}

	@HystrixCommand(fallbackMethod = "noop")
	@GetMapping("names")
	public List<String> getReservationNames() {
		return restTemplate.exchange("http://reservation-service/reservations",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<Resources<Reservation>>() {}
				)
				.getBody()
				.getContent()
				.stream()
				.map(Reservation::getName)
				.collect(Collectors.toList());
//		List<Reservation> reservations = new ArrayList<Reservation>(){};
	}

	@Autowired
	private Source source;

	@PostMapping("reservation")
	public void createReservation(@RequestParam String name) {
		Message<String> message = MessageBuilder.withPayload("name").build();
		source.output().send(message);

	}
}


@Data
class Reservation {
	private Long id;
	private String name;
}