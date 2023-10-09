package com.example.demo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


	@GetMapping("reservations/{id}")
	public Reservation get(@PathVariable Long id) {
		return repo.findById(id).get();
	}

	@Autowired
	private ReservationRepo repo;
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
}