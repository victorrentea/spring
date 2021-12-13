package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

	MyFacade(MyService myService, MyMapper myMapper) {
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
	private MyServiceOups myServiceOups;
	public void method() {

	}
}
@Service
class MyServiceOups {
	@Autowired
	private MyService myService;
	public void method() {

	}
}

@Component
class MyMapper {
//	public MyDto method(MyEntity e) {
//
//	}
}

@Repository
class MyRepo {
	public void method() {

	}
}




