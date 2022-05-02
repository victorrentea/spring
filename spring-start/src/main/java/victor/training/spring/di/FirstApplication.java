package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
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

	@Override
	public void run(String... args) throws Exception {
		System.out.println(x.prod());
	}
}

@Service
class X {
	// field injection
	@Autowired
	private Y y;

//	// method (setter) injection
	private Z z;

	@Autowired
	public void cumVreuEu(Z z) {
		this.z = z;
	}


	public int prod() {
		return 1 + y.prod();
	}
}



//@RequiredArgsConstructor
@Service
class Y {
	private final Z z;

	// constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z) {
		this.z = z;
	}

	public int prod() {
		System.out.println("Are y pe z? " + z.getClass());
		return 1 + z.prod();
	}
	public int callback() {
		return 0;
	}
}

//@Configuration contine metode @Bean
//@Repository // citeste/scrie din DB . Nu prea le mai folosim daca lucram cu Spring Data JPA
//@Controller // .jsp jsf velocity ? : genereaza HTML de pe server > nu prea e trendy. ci mai degraba ng/react/vue/
//@RestController // API de REST ✅

//@Component // tot restu
//@Service // "domain logic"

// a mea custom
@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Adapter {
}

@Adapter
class Z {
//	@Lazy
	private final Y y;
	private final Utility utility;
	public Z(@Lazy Y y, Utility utility) {
		this.y = y;
		this.utility = utility;
	}

	@Autowired
	ApplicationContext springContainer;

	public int prod() {
		System.out.println("Asta se intampla la runtime cand vine un req pe web (eg)");
		System.out.println("Are z pe y? " + y.getClass());

		Utility utility = springContainer.getBean(Utility.class); // in general code smell < mai putin in integrari intre frameworkuri
		// riscant pentru ca fetchul de ob la runtime poate crapa (eg ca nu ai definit beanul cautat).

		return utility.cevaStatic() + y.callback();
	}
}


//@Component
class Utility {
	public int cevaStatic() {
		return 1;
	}
}