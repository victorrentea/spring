package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import subp.X;

// [1] Injection: field, constructor, method; debate;
// 			mockito - injecteaza in acelasi mod
// [1] PostConstruct & co
// [2] Qualifier
// [6] inject List<BeanI>
// [3] Primary = cand ai mai multe beanuri care se potrivesc pe un punct de injectie cel marcat cu @Primary va castiga
// [4] Profile

// [5] getBean -> ApplicationContext#getBean

// [7] @Value (+Lombok @RAC) + @ConfigurationProperties

@SpringBootApplication
@ComponentScan(basePackages = {"subp", "victor.training.spring.di"})
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
