package victor.training.spring.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

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

	@Value("${x}")
	List<String> list;
//	@Value("${mapa}")
//	Map<String, Integer> map;
	@Override
	public void run(String... args) throws Exception {
		System.out.println(a);
		System.out.println("ws:  " + spagaMea);
		System.out.println("Lista: "+ list);
//		System.out.println("Lista: "+ map);
	}

	@Bean
	public WebServiceClient ldap(@Value("${secret:def}") String secret) {
		System.out.println("creez ldap : oare cine e this ?" + this);
//		this.securityProvider();
		return new WebServiceClient("ldap bla bla", securityProvider(), secret);
	}

	@Bean
	public WebServiceClient spaga(@Value("${secret:def}") String secret) {
		System.out.println("creez spaga");
		return new WebServiceClient("spaga bla bla", securityProvider(),secret);
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
	private final SecurityProvider securityProvider;
	private final String secret;

	public WebServiceClient(String endpoint, SecurityProvider securityProvider, String secret) {
		this.endpoint = endpoint;
		this.securityProvider = securityProvider;
		this.secret = secret;
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

