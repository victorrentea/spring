package victor.training.spring.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoreInjection implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MoreInjection.class);
	}

	@Autowired
	private A a;
	@Override
	public void run(String... args) throws Exception {
		System.out.println(a);
	}
}

