package victor.training.spring.ms.bootservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.stream.Stream;


@SpringBootApplication
public class BootServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BootServiceApplication.class, args);
	}

	@Autowired
	private ReservationRepo reservationRepo;

//	@Transactional
	@Override
	public void run(String... args) throws Exception {
		Stream.of("Aurelian","Valentin","Monica", "Tudor")
			.map(Reservation::new)
			.forEach(reservationRepo::save);
	}
}


