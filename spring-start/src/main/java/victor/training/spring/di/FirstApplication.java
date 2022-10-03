package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [3] Primary
// [4] Profile

//1 = prea lent
// 5=prea repede
// 3 = bun

// @Bean
// [2] Qualifier
// [5] getBean
// [6] inject List<BeanI>

@SpringBootApplication
public class FirstApplication /*implements CommandLineRunner*/{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired
	private X x;

//	@Override CommandLineRunner #1
//	public void run(String... args) throws Exception {
//	@PostConstruct // #2
	@EventListener(ApplicationStartedEvent.class)
	public void method() {
		System.out.println("init " +x.prod());
	}
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Adapter{}

//@Component // mapper, boring
//@Service // business logic < implem regulilor de prin spec

//@Configuration // vei gasi definitii de beanuri @Bean

//@Controller // pe vremuri se folosea .jsp, .jsf,  velocity
//@RestController // endpointuri Rest @GetMapping,...
//@Repository // DB sql/jpql / extends SpringDataJpa e suficient

//@MessageListener  cozi
@Slf4j
@RequiredArgsConstructor
@Adapter
class X {
	private final Y y;

	public int prod() {
		return 1 + y.prod();
	}
}
@Service
class Y {
	private final Z z;

	// constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z) {
		this.z = z;
		System.out.println("Si asta te rog .. ");
	}

	public int prod() {
		return 1 + z.prod();
	}
}
@Service
class Z {
	private final OInterfata interfata;

	Z(OInterfata interfata) {
		this.interfata = interfata;
		System.out.println("Ce mi-a fost injectat ? " + interfata.getClass());
	}

	public int prod() {
		return 1;
	}
}
interface OInterfata{ }

@Component
@Profile("test") // face beanul vizibil doar dc acest profil este activat
@Primary
class SecuritateDummyPtLocal implements OInterfata {}

@Component
//@Profile("!dev")
class SecuritatePeBunePentruProd implements OInterfata {}