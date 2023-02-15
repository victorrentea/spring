package victor.training.spring.integration.cafe;

import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.MessageEndpoint;
import victor.training.spring.integration.cafe.model.Delivery;
import victor.training.spring.integration.cafe.model.Drink;

import java.util.List;

@MessageEndpoint
public class Waiter {

	@Aggregator(inputChannel = "preparedDrinks", outputChannel = "deliveries")
	public Delivery prepareDelivery(List<Drink> drinks) {
		return new Delivery(drinks);
	}

	@CorrelationStrategy
	public int correlateByOrderNumber(Drink drink) {
		return drink.getOrderNumber();
	}

}
