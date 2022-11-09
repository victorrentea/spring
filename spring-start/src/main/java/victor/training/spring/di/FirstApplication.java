package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import subp.X;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>
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
//@Configuration// nu pentru clase ce contin logica de-a mea, ci doar pentru a defini @Bean sau ajusta defaulturi de prin spring

// semantica = layere
//@Service // defineste un bean in context de tip "X"
//@Repository // nu mai e necesar daca folosesti spring  Data (extinzi din JpaRepository/ Mongo.. / CrudRepository)

