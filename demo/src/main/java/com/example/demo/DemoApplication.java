package com.example.demo;

import lombok.Data;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("reservations/{id}")
	public Reservation get(@PathVariable Long id) {
		return repo.findById(id).get(); // in java >= 11 inlocuim cu .orElseThrow() =ok
	}

	@Value // lombok = campuri finale private + get
	public static class CreatePostRequest {
		@Size(min = 3)
		@NotNull
		String name;
	}

	@PostMapping("reservations")
	public Long create(@RequestBody @Validated CreatePostRequest request) {
		return repo.save(new Reservation(request.getName())).getId();
	}

	@GetMapping("reservations")
	public List<Reservation> getAll() {
		return repo.findAll();
	}

	@GetMapping("reservations/latest")
	public List<Reservation> method() {
		// CR: GET /reservation/latest sa intoarca ultimele n=5
		// rezervari create dupa creationDate (order by descendent), max.reservations citit din fisier .properties
		PageRequest pageRequest = PageRequest.of(0, size, Sort.Direction.DESC, "creationDate");
		return repo.findAll(pageRequest).getContent();
	}
@org.springframework.beans.factory.annotation.Value("${max.reservations}")
private int size;
	@Autowired
	private ReservationRepo repo;
}
@Component
@Profile("local") // class @Component; @Bean method; @Configuration classes
class DummyDataInserter {
	@Autowired
	private ReservationRepo repo;
	@PostConstruct
	public void insertData() {
		repo.save(new Reservation("Cip"));
		repo.save(new Reservation("Claudiu"));
	}
}
interface ReservationRepo extends JpaRepository<Reservation, Long> {
	List<Reservation> findByName(String name);

}
@Data // get set hash/eq tostr
@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	@Size(min = 3)
	private String name;
	private LocalDate creationDate = LocalDate.now();
	public Reservation() {	}
	public Reservation(String name) {
		this.name = name;
	}
}