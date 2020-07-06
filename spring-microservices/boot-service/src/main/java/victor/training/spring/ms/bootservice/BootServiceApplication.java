package victor.training.spring.ms.bootservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Entity;
import javax.persistence.Id;

@SpringBootApplication
public class BootServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootServiceApplication.class, args);
	}

}


@Entity
class Reservation {
	@Id
	private Long id;
	private String name;
	protected Reservation() {}

	public Reservation(Long id) {
		this.id = id;
	}
}