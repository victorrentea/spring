package victor.training.spring.events.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import victor.training.spring.first.FirstApplication;
import victor.training.spring.first.UnRepo;

interface Channels {
    String Q1_OUT = "rsout";
    @Output(Q1_OUT)
	 MessageChannel rsout();
}

@Slf4j
@EnableBinding(Channels.class)
@SpringBootApplication
@Import(FirstApplication.class)
public class OrderServiceApp implements CommandLineRunner {
	public static void main(String[] args) {
	    SpringApplication.run(OrderServiceApp.class, args);
	}
	@Autowired
	UnRepo unRepo;


	@Autowired
	private Channels channels;

	public void placeOrder() {
		log.debug(">> PERSIST new Order");
		long orderId = 13L;

		channels.rsout().send(MessageBuilder.withPayload("" + orderId).build());

	}

	@Override
	public void run(String... args) throws Exception {
		placeOrder();
	}
}
