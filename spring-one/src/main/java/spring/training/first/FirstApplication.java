package spring.training.first;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAsync
public class FirstApplication {
	
	//cream manual o instanta de Person invocand dupa gust constructorul, 
	//sau facand ce alte initializari mai aveam nevoie
	// @Primary // Oridecate ori Spring trebuie sa aleaga intre asta si altu, il ia pe asta !
	@Bean
	public Person emma() {
		return new Person("Emma");
	}
	@Bean
	@Mezinul
	public Person vlad() {
		return new Person("Vlad");
	}
	
	@Bean
	public ClasaPeCareNuOPotiModifica p() { // NU UMBLAM la numele de metode @Bean
		// dintro metoda @Bean poti creea orice clasa iti tuna. chiar daca nu ii controlezi codul.
		return new ClasaPeCareNuOPotiModifica("p");
	}
	
	@Bean
	public DeMana r() {
		return DeMana.getInstance();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@interface Mezinul {}


@Retention(RetentionPolicy.RUNTIME)
@Component
@interface Facade {}

@Facade
//@Service
//@Repository
//@Controller
//@RestController
class A implements CommandLineRunner {
	@Autowired
	private B b;
	
	@Autowired
	private ApplicationContext spring;
	
	@Autowired
	@Mezinul
//	@Qualifier("vlad")
	private Person person;
	
	@Autowired
	private List<Person> totiCopii;

	public void run(String... args) throws Exception {
		System.out.println("Hello Spring Boot!");
		b.bye();
		
		//acest nume de bean rezulta din numele metodei marcate cu @Bean "emma()". Fratzicule!
		Person emma = (Person) spring.getBean("emma");
		System.out.println("Pe fii-mea o cheama : " + emma.getName());
		Person vlad = (Person) spring.getBean("vlad");
		System.out.println("Pe fii-miu o cheama : " + vlad.getName());
		
		//by default, numele oricarui @Component/... adaugat 
		// automat este lower-CamelCase al numelui clasei
		if (Math.random() > 0.5) {
			System.out.println(spring.getBean("dacaEra")); // URAT!!! cum sa pui nume de clasa intre "ghilimele"
			// getBean(String) facut la runtime e periculos, ca poate nu cazi in eroare
			// decat in Productie
			// spring.getBean("dacaEraXXX")
			DacaEra dacaEra = spring.getBean(DacaEra.class);
			System.out.println("Bean luat dupa tip: " + dacaEra);
		}
//		System.out.println(spring.getBean("dacaEra"));
		
		System.out.println("CopilUL: " + person);
		
		System.out.println("Toti copiii: " + totiCopii);
	}
}

class Person {
	private final String name;
	public Person(String name) {
		this.name = name;
	}
	
	@PostConstruct
	public void nastere() {
		System.out.println("Uaaaa!");
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return "Person [name=" + name + "]";
	}
	
}


@Component
class B {
	private C c;
	private DacaEra d;
	// Daca Spring vede o clasa @Component/Service care are un singur constructor 
	// va incerca automat sa apeleze acel constructor cu bean-uri compatibile din "galeata"
 	@Autowired // AUtowired poate selecta dintre 2 constructori cu parametrii pe acela pe care-l veau invocat
	public B(C c, DacaEra d) {
		this.c = c;
		this.d = d;
	}
 	public B(C c) {
 		this.c = c;
 	}

 	@Autowired
	public void nuoface(C c, DacaEra d) {
 		System.out.println("Pentru ca e mai bine cu @PostConstruct: " + c + d);
 	}
	

	public void bye() {
		System.out.println("Bye Spring Boot!");
	}
}

@Component
class C {}
@Component
class DacaEra {
	@Autowired
	private ClasaPeCareNuOPotiModifica c;
	
	@Autowired
	private DeMana deMana;
	
	public DacaEra() {
		System.out.println("ce puii mei e asta!?! : " + deMana);
	}
	
	@PostConstruct
	public void cePotSaFacInPostConstruct() {
		//poti folosi dependentele injectate pe campuri
		System.out.println("Sunt injectat: " + deMana);
		// incarc din baza config
		// apelez un serviciu web "am pornit"
		// chestii de facut la start-up-ul aplicatiei.
	}
}


class ClasaPeCareNuOPotiModifica { // nu am voie sa o ating
	private String param;

	public ClasaPeCareNuOPotiModifica(String param) {
		this.param = param;
	}
}


class DeMana {
	private static DeMana INSTANCE;
	private DeMana() {
	}
	public static DeMana getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DeMana();
		}
		return INSTANCE;
	}
}

@Component
class Monitor implements SmartLifecycle, LifecycleProcessor {

	public boolean isAutoStartup() {
		return false;
	}
	public void start() {
		 System.out.println("Start");
	}

	public void stop() {
		System.out.println("Stop");
	}

	public boolean isRunning() {
		System.out.println("is running");
		return true; // TODO
	}
	public void onRefresh() {
		 // TODO
	}
	public void onClose() {
		 System.out.println("onClose");
	}
	
	
}