package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>

@SpringBootApplication
//@ComponentScan(basePackages = {"victor.training.spring.web","victor.training"})
public class FirstApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}


	@Override
	public void run(String... args) throws Exception {
		// spring: integrari cu framework, DI, boilerplate protection,
		// promoveaza niste principii de design

	}
}

//@Controller // pt jsp/jsp/
@RestController
class MyController {
	private final MyFacade facade;

	MyController(MyFacade facade) {
		this.facade = facade;
	}
@GetMapping
	public String method() {
		return "Hello spring";
	}
}


@Retention(RetentionPolicy.RUNTIME)
@Component
@interface FacadeX {

}

@FacadeX
class MyFacade {
	private final MyService myService;
	private final MyMapper myMapper;

	public MyFacade(MyService myService, MyMapper myMapper) {
		this.myService = myService;
		this.myMapper = myMapper;
	}

	public void method() {

	}
}
@Service
class MyService {
	@Autowired
	private MyRepo myRepo;
	@Autowired
	private MyServiceOups myServiceOups; // nu e frumos, dar @Autowired permite dep circulare

	public void method() {

	}
}
@Service
class MyServiceOups {
	private final MyService myService;
	private final MyService myService2;
	private final MyService myService3;
	private final MyService myService4;
	private final MyService myService5;
	private final MyService myService6;
	private final MyService myService7;
	private final MyService myService8;
	private final MyService myService9;

	public MyServiceOups(MyService myService, MyService myService2, MyService myService3, MyService myService4, MyService myService5, MyService myService6, MyService myService7, MyService myService8, MyService myService9) {
		this.myService = myService;
		this.myService2 = myService2;
		this.myService3 = myService3;
		this.myService4 = myService4;
		this.myService5 = myService5;
		this.myService6 = myService6;
		this.myService7 = myService7;
		this.myService8 = myService8;
		this.myService9 = myService9;
	}

	public void method() {

	}
}

class DoamneAjutaUnTest {
	void test() {
//		MyServiceOups oups = new MyServiceOups();
	}
}

@Component
class MyMapper {
//	public MyDto method(MyEntity e) {
//
//	}
}

@Configuration
class JavaSecurityConfig {
}



@Repository
class MyRepo {

	private TransactionTemplate transactionTemplate;
	private LazyInit lazyInit;

	@Autowired
	public void setLazyInit(LazyInit lazyInit) {
		this.lazyInit = lazyInit;
	}

	@Autowired
	public void setTransactionTemplate(PlatformTransactionManager platformTransactionManager) {
		this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
		transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW"); // @Transactional (propagation = REQUIRES_NEW)
	}

	public void method() {
		transactionTemplate.executeWithoutResult(x -> {
			// cod in tranzactie  JDBC separata.
		});
	}
}

@Component
@Lazy // de ce vreodata ? >> daca am de scos din baza ceva
	// nu prea e best practice lazy.
class LazyInit {
	@PostConstruct
	public void method() {
		System.out.println("LOAD DIN DB");
		// gandul era ca LOAD DIN DB sa-l aman pana cand vine primul request care are nevoie pe bune de aceste date.
		// @Autowired intr-un bean non-lazy cauzeaza beanul asta sa fie init de la inceput.
	}
}




