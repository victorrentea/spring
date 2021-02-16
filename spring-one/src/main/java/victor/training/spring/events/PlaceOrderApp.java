package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import victor.training.spring.events.order.OrderService;

@Slf4j
//@EnableBinding({Queues.class})
@SpringBootApplication
public class PlaceOrderApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(PlaceOrderApp.class, args);
	}

	@Bean
	public ApplicationEventMulticaster applicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return eventMulticaster;
	}


	// TODO [1] Decouple using @EventListener and ApplicationEventPublisher
	// TODO [2] also generate invoice
	// TODO [2] control the @Order
	// TODO [3] event chaining
	// TODO [4] handle asynchronously: exceptions, Tx, Thread Scope, debate
	// TODO [5] queues: retries, chaining, topics
	// TODO [opt] Transaction-scoped events
	public void run(String... args) {
		orderService.placeOrder();
	}
	@Autowired
	OrderService orderService;
}


/// ----------- visez ca peste X ani de aici in jos mut in alta aplicatie (microserviciu)

