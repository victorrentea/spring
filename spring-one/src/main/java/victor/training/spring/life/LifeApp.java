package victor.training.spring.life;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LifeApp implements CommandLineRunner{
	@Bean
	public static CustomScopeConfigurer defineThreadScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		// WARNING: Leaks memory. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html
		configurer.addScope("thread", new SimpleThreadScope());


//		TODO: exact cum se curata
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
		new Thread(() -> exporter.export(Locale.ENGLISH)).start();
		new Thread(() -> exporter.export(Locale.FRENCH)).start();
	}
}

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
class MyRequestContext {
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
@Service
class OrderExporter  {
	private static final Logger log = LoggerFactory.getLogger(OrderExporter.class);
	@Autowired
	private InvoiceExporter invoiceExporter;
	@Autowired
	private LabelService labelService;
	@Autowired
	private MyRequestContext requestContext;

	public void export(Locale locale) {
		log.debug("Running export in " + locale);
		labelService.load(locale);
		System.out.println("Sus: " + requestContext.getClass());
		requestContext.setUsername("sys");

		try {
			log.debug("BIZ LOGIC. Origin Country: " + labelService.getCountryName("rO"));
			altaMet();
		} finally {
//			ClearableThreadScope.clearAllThreadData();
		}
	}
	private void altaMet() { // semnaturi curate si uscate.
		altaMetDinAltaClasa();
	}
	private void altaMetDinAltaClasa() {
		invoiceExporter.exportInvoice();
	}
}
@Service
class InvoiceExporter {
	private static final Logger log = LoggerFactory.getLogger(OrderExporter.class);
	@Autowired
	private LabelService labelService;
	public void exportInvoice() {
		// asta se intampla pe sub defapt
//		labelService = new LabelService(null) {
//			@Override
//			public String getCountryName(String iso2Code) {
		// gaseste threadul curent Thread.currentThread()
		// ia-i datele de pe java.lang.ThreadLocal
//				return super.getCountryName(iso2Code);
//			}
//		};
		log.debug("Ce puii mei mi-a injectat springu aici, de eu can chem ajung pe 2 instante? " +
				labelService.getClass());
		log.debug("BIZ LOGIC. Invoice Country: " + labelService.getCountryName("ES"));
	}
}

// BAD PRACTICE: ciircular dependencies
@Component
class A {
	@Autowired
	private B b;
}
@Component
class B {
	@Autowired
	private A a;
}

@Service
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS) // ori de cate ori ai nevoie de o instanta din clasa asta, creeaza una noua
// Spring + OOP = HATE
class LabelService {
	private static final Logger log = LoggerFactory.getLogger(OrderExporter.class);
	private final CountryRepo countryRepo;
	private final MyRequestContext requestScope;

	public LabelService(CountryRepo countryRepo, MyRequestContext requestScope) {
		this.requestScope = requestScope;
		System.out.println("jos: " + requestScope.getClass());
		System.out.println("+1 Label Service: " + this.hashCode());
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames;

//	@PostConstruct
	public void load(Locale locale) {
		log.debug("LabelService.load() on instance " + this.hashCode());
		log.debug("UPDATE LAST_MODIFIED_BY=" + requestScope.getUsername());
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}
	
	public String getCountryName(String iso2Code) {
		log.debug("LabelService.getCountryName() on instance " + this.hashCode());
		return countryNames.get(iso2Code.toUpperCase());
	}
}