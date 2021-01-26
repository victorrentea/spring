package victor.training.reservationmicroservice;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.test.FixedSecureRandom.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
@Slf4j
@EnableDiscoveryClient
@RequiredArgsConstructor
@SpringBootApplication
@EnableBinding(Sink.class)
public class ReservationMicroserviceApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ReservationMicroserviceApplication.class, args);
	}




@Autowired
	private  ReservationRepo repo;
	@Override
	public void run(String... args) throws Exception {

		Stream.of("Stefania", "Dan", "Bianca", "Razvan").map(Reservation::new).forEach(repo::save);
	}
}

@Slf4j
@MessageEndpoint
@RequiredArgsConstructor
class CreateReservationHandler {
	private final ReservationRepo repo;
	@ServiceActivator(inputChannel = Sink.INPUT)
	public void method(String name) {
		log.info("Hooray: creating " + name);
		Reservation entity = new Reservation();
		entity.setName(name);
		repo.save(entity);
	}
}

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
class ReservationController {
	private final ReservationRepo repo;
	@Value("${message}")
	private String per;

	@PostConstruct
	public void method() {
		log.info("per: {}", per);
	}
	@GetMapping("hello")
	public String message() {
		return per;
	}

	@GetMapping
	public List<ReservationDto> getAll() {
		log.debug("get all ");

		return repo.findAll().stream().map(ReservationDto::new).collect(toList());
	}

	@GetMapping("{id}")
	public ReservationDto getById(@PathVariable long id) {
		Reservation entity = repo.findById(id).get();
		return new ReservationDto(entity);
	}

	@PostMapping
	public void create(@RequestBody ReservationDto dto) {
		Reservation entity = new Reservation();
		entity.setName(dto.name);
		repo.save(entity);
	}
}

class ReservationDto {
	public Long id;
	public String name;
	public ReservationDto() {} // for jackson love
	public ReservationDto(Reservation entity) {
		id = entity.getId();
		name = entity.getName();
	}
}

interface ReservationRepo extends JpaRepository<Reservation, Long> {
}

@Data // never in prod: no@Data on @Entity
@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	public Reservation(String name) {
		this.name = name;
	}
	public Reservation() {}
}