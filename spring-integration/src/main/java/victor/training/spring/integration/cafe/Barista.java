package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.integration.cafe.model.Drink;
import victor.training.spring.integration.cafe.model.OrderItem;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class Barista {
	private final AtomicInteger hotDrinkCounter = new AtomicInteger();
	private final AtomicInteger coldDrinkCounter = new AtomicInteger();

	public Drink prepareHotDrink(OrderItem orderItem) {
		try {
			log.info("Preparing hot drink...");
			Thread.sleep(3000);
			log.info("Prepared hot drink #" + hotDrinkCounter.incrementAndGet() + " for order #"
					+ orderItem.getOrderNumber() + ": " + orderItem);
			return new Drink(orderItem.getOrderNumber(), orderItem.getDrinkType(), orderItem.isIced(),
					orderItem.getShots());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

//	@ServiceActivator(inputChannel="coldDrinkBarista", outputChannel="preparedDrinks")
	public Drink prepareColdDrink(OrderItem orderItem) {
		try {
			log.info("Preparing cold drink...");
//			if (Math.random() < .5) {
//				throw new IllegalArgumentException("Out of ice !!");
//			}
			Thread.sleep(1000);
			log.info("Prepared cold drink #" + coldDrinkCounter.incrementAndGet() + " for order #"
					+ orderItem.getOrderNumber() + ": " + orderItem);
			return new Drink(orderItem.getOrderNumber(), orderItem.getDrinkType(), orderItem.isIced(),
					orderItem.getShots());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

}
