package com.example.demo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// TODO  sa dea 404 cand nu gaseste ID
	@GetMapping("reservations/{id}")
	public ResponseEntity<Reservation> get(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(repo.findById(id).get());
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
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