package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
@Service // Springul se va ocupa sa creeze si sa configureze (injecteze) acest obiect.
//@RequiredArgsConstructor
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

@Service
//@Lazy
class Z {
//	@Lazy
	private final Y y;
	public Z(@Lazy Y y) {
		this.y = y;
	}
	public int prod() {
		System.out.println("Are z pe y? " + y.getClass());
		return Utility.cevaStatic() + y.callback();
	}
}


class Utility {
	public static int cevaStatic() {
		return 1;
	}
}