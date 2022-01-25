package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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

	}
}
@RequiredArgsConstructor
@Service
class X {
//	@Autowired
//	private Y y;
	private final Y y;

	@Value("${some.url}")
	private String someUrl;

	public int toTest() {
		int i = y.something();
		return i + 1;
	}
}


@Service
class Y { // <bean class="victor.training.spring.first.Y" >

	private Z z; // <property name="z" ref="z" />
	private Z z2;

	@Autowired // never used EXCEPT
	public void whatever(Z z, Z z2) {
		this.z = z;
		this.z2 = z2;
	}

	TransactionTemplate txTemplate;

	@Autowired
	public  void setTxTemplate(PlatformTransactionManager txManager) {
		txTemplate = new TransactionTemplate(txManager);
		txTemplate.setPropagationBehaviorName("REQUIRES_NEW");
	}

	public int something() {
		txTemplate.executeWithoutResult(s -> {
			// piece of code in a new tx
		});

		throw new IllegalArgumentException(" NOT OT CALL IN TESTS");
	}
}


@Service
class Z {

}