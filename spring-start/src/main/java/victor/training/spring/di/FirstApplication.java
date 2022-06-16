package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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

	@Autowired
	private X x;
	@Autowired
	private Y y;
	@Autowired
	OInterfata i ;
	@Value("${prop}")
	String p;
	@Value("${prop2}")
	String pneschimbata;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(x.prod());
		System.out.println(i.getClass());
		System.out.println(p);
		System.out.println(pneschimbata);
	}
}

@Slf4j
@RequiredArgsConstructor
//@Slf4j
@Service
class X {
	// field injection
	private final Y y;
	private final Z z;
	// method (setter) injection (rarely used)
//	private Z z;
//	@Autowired
//	public void setZ(Z z) {
//		this.z = z;
//	}
	@Autowired
	private ApplicationContext applicationContext;
//	@Autowired
//	private PrototipuMeu prototipuMeu; // PRAF CVALE.

	public int prod() {
		log.debug("ceva");
		PrototipuMeu nou = applicationContext.getBean(PrototipuMeu.class);// daca PrototipuMeu e @Scope("prototype") > obtii o instanta noua de fiecare data
//		cuiva(nou);
		return 1 + y.prod();
	}
}
@Service
@Scope("prototype")
class PrototipuMeu {
//	@Autowired
	private String dateIntermediareDintrOProcesareComplexa;
	private String currentUsername;// pe camp, STATE FULL  design

	public void setCurrentUsername(String currentUsername) {
		this.currentUsername = currentUsername;
	}
//@Transactional
	public void curr() {
		System.out.println(currentUsername);
	}
}

@Service
//@ApplicationScoped // CDI (JavaEE)
//@Scope("singleton")
//@Scope("prototype")
//@Scope("session") .. a plecat starea ecranului in JS
//@Scope("request") // fiecare request HTTP care vine primeste o instanta noua din asta. usecase: extragi din http request header si le pui pe bean request scoped pt ca mai apoi in acealsi thread WEB sa fie disponibile oricn vrea din app ta.
class Y {
	private final Z z;

	// constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z) {
		this.z = z;
	}

	public int prod() {
		return 1 + z.prod();
	}
}
@Service
class Z {
//	@Autowired
//private Y y;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	private Alta alta;

	public int prod() {
		Z z = applicationContext.getBean(Z.class);
//		Alta alta = applicationContext.getBean(Alta.class);
		return 1;
	}
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {
}

@Facade // Application Service (DDD)
//@Service // business logic
//	@Component // << ceva asa gen Util de nu8 stii unde sa pui o line de co d.. o lasi acolo
//@Repository nu mai folosim de Sprjng data
//@Controller nu mai folosim ca REST
//@RestController REST
//@Configuration
class Alta {

}