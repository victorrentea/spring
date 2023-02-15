package victor.training.spring.integration.cafe.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Delivery implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final String SEPARATOR = "-----------------------";

	private List<Drink> deliveredDrinks;

	private int orderNumber;

	// Default constructor required by Jackson Java JSON-processor
	public Delivery() {}

	public Delivery(List<Drink> deliveredDrinks) {
		assert(deliveredDrinks.size() > 0);
		this.deliveredDrinks = deliveredDrinks;
		this.orderNumber = deliveredDrinks.get(0).getOrderNumber();
	}


	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<Drink> getDeliveredDrinks() {
		return deliveredDrinks;
	}

	public void setDeliveredDrinks(List<Drink> deliveredDrinks) {
		this.deliveredDrinks = deliveredDrinks;
	}

	@Override
	public String toString() {
		return SEPARATOR + "\n" + "Delivery for Order #" + getOrderNumber() + "\n"  +
			getDeliveredDrinks()
				.stream().map(drink -> drink + "\n")
				.collect(Collectors.joining("")) +
			SEPARATOR + "\n";
	}

}
