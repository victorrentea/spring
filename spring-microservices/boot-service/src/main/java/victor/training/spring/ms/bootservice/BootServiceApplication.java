package victor.training.spring.ms.bootservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
		reservationRepo.save(new Reservation("John"));
	}
}

interface ReservationRepo extends JpaRepository<Reservation, Long> {

}


@Entity
class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	protected Reservation() {}

	public Reservation(String name) {
		this.name = name;
	}
}