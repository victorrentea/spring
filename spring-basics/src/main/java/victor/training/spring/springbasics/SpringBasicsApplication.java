package victor.training.spring.springbasics;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SpringBasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBasicsApplication.class, args);
	}
}

@Component // @Service @Repository @Controller @RestController @MessageEndpoint
class AutoRun implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello Spring!");
	}
}