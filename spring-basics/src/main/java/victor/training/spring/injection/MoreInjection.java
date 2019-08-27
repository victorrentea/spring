package victor.training.spring.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class MoreInjection implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MoreInjection.class);
	}

	@Autowired
	//-Dspring.profiles.active=masina.mea
	private ALogin a;
	@Qualifier("spaga")
	@Autowired
	private WebServiceClient spagaMea;
	@Override
	public void run(String... args) throws Exception {
		System.out.println(a);
		System.out.println("ws:  " + spagaMea);
	}

	@Bean
	public WebServiceClient ldap() {
		return new WebServiceClient("ldap bla bla");
	}

	@Bean
	public WebServiceClient spaga() {
		return new WebServiceClient("spaga bla bla");
	}
}

// ldap spaga
class WebServiceClient {
	private final String endpoint;

	public WebServiceClient(String endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public String toString() {
		return "WebServiceClient{" +
				"endpoint='" + endpoint + '\'' +
				'}';
	}
}

