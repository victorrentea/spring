package victor.training.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AttackerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AttackerApplication.class)
				.run(args);
	}
}
