package victor.training.spring.integration.cafe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	/** the order number used for tracking */
	private int number;

	// Default constructor required by Jackson Java JSON-processor
	public Order() {}

	public Order(int number) {
		this.number = number;
	}

	public void addItem(DrinkType drinkType, int shots, boolean iced) {
		this.orderItems.add(new OrderItem(this.number, drinkType, shots, iced));
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<OrderItem> getItems() {
		return this.orderItems;
	}

	public void setItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderItems=" + orderItems +
				", number=" + number +
				'}';
	}
}
