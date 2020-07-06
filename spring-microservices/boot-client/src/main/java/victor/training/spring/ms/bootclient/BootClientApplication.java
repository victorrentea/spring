package victor.training.spring.ms.bootclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class BootClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootClientApplication.class, args);
	}

}


