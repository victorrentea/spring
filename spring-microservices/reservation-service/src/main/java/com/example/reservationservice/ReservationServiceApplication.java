package com.example.reservationservice;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.stream.Stream;

@IntegrationComponentScan
@EnableBinding(Sink.class)
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

	@Autowired
	private ReservationRestRepository repo;

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		Stream.of("John", "Marius", "Alex").map(Reservation::new).forEach(repo::save);
	}
}

@RepositoryRestResource(path = "/reservations")
interface ReservationRestRepository extends JpaRepository<Reservation, Long> {
}

@RefreshScope
@RestController
class MessageController {
	@Value("${message}")
	private String message;

	@Autowired
    private ReservationRestRepository repo;

	@GetMapping("message")
	public String message() {
		return message;
	}

	@PostMapping("/reservations/create")
    public void createReservation(@RequestBody Reservation reservation) {
	    repo.save(reservation);
    }
}


@MessageEndpoint
class ReservationCreator {
	@Autowired
	private ReservationRestRepository repo;

	@ServiceActivator(inputChannel = "input")
	public void acceptNewReservation(String rn) {
		System.out.println("Message received.");
		repo.save(new Reservation(rn));
	}
}

@Data
@Entity
class Reservation {
	@GeneratedValue
	@Id
	private Long id;
	private String name;
	private Reservation() {
	}
	public Reservation(String name) {
		this.name = name;
	}
}