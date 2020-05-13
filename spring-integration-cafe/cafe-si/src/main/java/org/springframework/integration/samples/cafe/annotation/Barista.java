package org.springframework.integration.samples.cafe.annotation;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.samples.cafe.Drink;
import org.springframework.integration.samples.cafe.OrderItem;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Barista {

	private static Log logger = LogFactory.getLog(Barista.class);

	private long hotDrinkDelay = 5000;

	private long coldDrinkDelay = 1000;

	private final AtomicInteger hotDrinkCounter = new AtomicInteger();

	private final AtomicInteger coldDrinkCounter = new AtomicInteger();


	public void setHotDrinkDelay(long hotDrinkDelay) {
		this.hotDrinkDelay = hotDrinkDelay;
	}

	public void setColdDrinkDelay(long coldDrinkDelay) {
		this.coldDrinkDelay = coldDrinkDelay;
	}

	@ServiceActivator(inputChannel="hotDrinkBarista", outputChannel="preparedDrinks")
	public Drink prepareHotDrink(OrderItem orderItem) {
		try {
			Thread.sleep(this.hotDrinkDelay);
			logger.info(Thread.currentThread().getName()
					+ " prepared hot drink #" + hotDrinkCounter.incrementAndGet() + " for order #"
					+ orderItem.getOrderNumber() + ": " + orderItem);
			return new Drink(orderItem.getOrderNumber(), orderItem.getDrinkType(), orderItem.isIced(),
					orderItem.getShots());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	@ServiceActivator(inputChannel="coldDrinkBarista", outputChannel="preparedDrinks")
	public Drink prepareColdDrink(OrderItem orderItem) {
		try {
			Thread.sleep(this.coldDrinkDelay);
			logger.info(Thread.currentThread().getName()
					+ " prepared cold drink #" + coldDrinkCounter.incrementAndGet() + " for order #"
					+ orderItem.getOrderNumber() + ": " + orderItem);
			return new Drink(orderItem.getOrderNumber(), orderItem.getDrinkType(), orderItem.isIced(),
					orderItem.getShots());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

}
