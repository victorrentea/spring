package victor.training.spring.bean;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


// A bit more advanced

@EnableCaching
@Component
public class LiteMode {
	@Bean
	public R r1(P p) {
		System.out.println("Got p: " + p.getClass());
		return new R();
	}
	@Bean
	public P p1() {
		return new P();
	}
}

class R{}
class P {
	//@Cacheable("stuff")
	public int m() {
		return 0;
	}
}
