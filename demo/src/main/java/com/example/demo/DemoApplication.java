package com.example.demo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// TODO sa intoacem un GetReservationResponse alta clasa construita din entitate
	@GetMapping("reservations/{id}")
	public Reservation get(@PathVariable Long id) {
		return repo.findById(id).get();
	}
	@PostMapping("reservations")
	public Long create(@RequestParam String name) {
		return repo.save(new Reservation(name)).getId();
	}

	@Autowired
	private ReservationRepo repo;

	@PostConstruct
	public void insertData() {
		repo.save(new Reservation("Cip"));
		repo.save(new Reservation("Claudiu"));
	}
}
interface ReservationRepo
		extends JpaRepository<Reservation, Long> {}
@Data // get set hash/eq tostr
@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private LocalDate creationDate;
	public Reservation() {	}
	public Reservation(String name) {
		this.name = name;
	}
}