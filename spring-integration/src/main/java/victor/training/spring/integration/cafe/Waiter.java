package victor.training.spring.integration.cafe;

import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.MessageEndpoint;
import victor.training.spring.integration.cafe.model.Delivery;
import victor.training.spring.integration.cafe.model.Drink;

import java.util.List;

@MessageEndpoint
public class Waiter {
	@CorrelationStrategy
	public int correlateByOrderNumber(Drink drink) {
		return drink.getOrderNumber();
	}
// <int:aggregator input-channel="aggregate" output-channel="out" message-store="store" release-strategy="releaser" /> // #1 XML
//	@Aggregator(inputChannel = "drinks", outputChannel = "deliveries") // form #2 annotations on java code
	@Aggregator // form #3 - IntegrationFlows
	public Delivery prepareDelivery(List<Drink> drinks) {
		return new Delivery(drinks);
	}


}
