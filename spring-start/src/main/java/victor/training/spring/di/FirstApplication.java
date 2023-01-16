package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.di.sub.Noua;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>
// [7] @Value (+Lombok @RAC) + @ConfigurationProperties

@SpringBootApplication
public class FirstApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired // ii cere lui spring sa injecteze aici o isntanta de tipu X - field injection
	private X x;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(x.prod());
	}
}
//@Repository // DB
//@Controller // http NU se mai foloseste azi decat daca serverul genereaza HTML .jsp jsf vaadin
//@RestController // http API REST
// layerul de servicii  cu logica de aplicatie utila

//@Component // gunoi
@Slf4j
@Service // ii spune lui Spring sa creeze un bean de acest tip si sa-l puna in container
@RequiredArgsConstructor // injectie prin constructor invizibil  = crema
class X {

//	@Autowired // nicioadata nu se mai foloseste azi
//	public void setY(Y y) {
//		this.y = y;
//	}

	// e tricky la testare
	private final Noua noua;

	// #1 field injection
	private final Y y;

	// #2 method (setter) injection (rarely used)
	//	private Z z;
	//	@Autowired
	//	public void setZ(Z z) {
	//		this.z = z;
	//	}

	public int prod() {
		return 1 + y.prod();
	}
}
@Service
@RequiredArgsConstructor
class Y {
	private final Z z;
	@Value("${db.password:-1}") // cu default value
	private int timeout;

	@Lazy
	private final X x;

//	Y(Z z, @Lazy X x) {
//		this.z = z;
//		this.x = x;
//	}

	//	@Value("${welcome.welcomeMessage}")
//	String welcomeMessage;
//	@Value("${welcome.help.app-id}")
//	String appId;

	public int prod() {
		return 1 + z.prod() + timeout;
	}
}
@Service
class Z {

	public int prod() {
		return 1;
	}
}