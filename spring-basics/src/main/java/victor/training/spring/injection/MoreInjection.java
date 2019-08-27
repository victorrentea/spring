package victor.training.spring.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
//@Configuration // doar intr-o astfel de clasa @Bean iti da fiori: ti-o suprascrie Spring automat pentru a face lifecycle hooks+injection+aspects
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
		System.out.println("creez ldap : oare cine e this ?" + this);
//		this.securityProvider();
		return new WebServiceClient("ldap bla bla", securityProvider());
	}

	@Bean
	public WebServiceClient spaga() {
		System.out.println("creez spaga");
		return new WebServiceClient("spaga bla bla", securityProvider());
	}
	@Bean
	public SecurityProvider securityProvider() {
		System.out.println("Creez sec prov");
		SecurityProvider securityProvider = new SecurityProvider();
		securityProvider.gadila();
		return securityProvider;
	}
}
//class Hack extends MoreInjection {
//	@Override
//	public SecurityProvider securityProvider() {
//		//tzeapa
//	}
//}

// ldap spaga
class WebServiceClient {

	private final String endpoint;
	private SecurityProvider securityProvider;

	@Value("${secret}")
	private String secret;

	public WebServiceClient(String endpoint, SecurityProvider securityProvider) {
		this.endpoint = endpoint;
		this.securityProvider = securityProvider;
	}

	@Override
	public String toString() {
		return "WebServiceClient{" +
				"secret='" + secret + '\'' +
				", endpoint='" + endpoint + '\'' +
				", securityProvider=" + securityProvider +
				'}';
	}
}

