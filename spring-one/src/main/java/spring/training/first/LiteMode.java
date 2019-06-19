package spring.training.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
	@Async
	public void m() {
		
	}
}	
