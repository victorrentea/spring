package victor.training.spring.life;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class LifeApp implements CommandLineRunner{
	@Bean
	public static CustomScopeConfigurer defineThreadScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		// WARNING: Can leaks memory if data remains on thread. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html
		// TODO finally { ClearableThreadScope.clearAllThreadData(); }
		configurer.addScope("thread", new ClearableThreadScope());
		return configurer;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LifeApp.class);
	}
	
	@Autowired 
	private OrderExporter exporter;
	
	// TODO [1] make singleton; multi-thread + mutable state = BAD
	// TODO [2] instantiate manually, set dependencies, pass around; no AOP
	// TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
	// TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc

	public void run(String... args) {
		new Thread(() ->exporter.export(Locale.ENGLISH)).start();
		new Thread(() ->exporter.export(Locale.FRENCH)).start();
	}
}
@Slf4j
@Service
@RequiredArgsConstructor
class OrderExporter  {
//	@Autowired
//	@Lazy
//	private LabelService labelService; //pacaleala: o singura data are nevoie Spr de o instanta de LabelService, pt ca OrderExporter e singleton.

	@Autowired
	private ApplicationContext applicationContext;


	public void export(Locale locale) {
		// la fiecare invocare CERI springului o instanta noua, care ramane pe acel apel de functie. ("privata threadului curent")
		LabelService labelService = applicationContext.getBean(LabelService.class);
		log.debug("Running export in " + locale);
		labelService.load(locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO")); 
	}
}
//@Slf4j
//@Service
//@RequiredArgsConstructor
//class InvoiceExporter {
//	private final LabelService labelService;
//
//	public void exportInvoice() {
//		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
//	}
//}

@Slf4j
//@Service // by default, toate beanurile spring sunt singletoane (1 instanta / aplicatie)
//@Scope(value = "prototype") // de fiecare data cand spring va avea nevoie de o instanta din asta, va creea o noua instanta.
class LabelService {
	private final CountryRepo countryRepo;
	
	public LabelService(CountryRepo countryRepo) {
		log.debug(this + ".new()");
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames;

//	@PostConstruct
	public void load(Locale locale) {
		log.debug(this + ".load()");
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}
	
	public String getCountryName(String iso2Code) {
		log.debug(this + ".getCountryName()");
		return countryNames.get(iso2Code.toUpperCase());
	}
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}

@Getter
@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
class MetadateDeRequest {
	private String username; // SecurityContextHolder.getContext().....
	private Locale userLocale;
	private int userTimezone;
	private int userRole;

	private int clientId; // NU-s mereu date pe toate fluxurile
	private int accountId;// NU-s mereu date pe toate fluxurile
}

@Service
class SOmeSerice {
	@Autowired
	private MetadateDeRequest metadate;

	public void method() {
		System.out.println(metadate.getUserLocale());
		System.out.println(metadate.getAccountId());
	}
}