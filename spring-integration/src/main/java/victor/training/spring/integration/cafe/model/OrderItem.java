package victor.training.spring.integration.cafe.model;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private DrinkType type;

	private int shots = 1;

	private boolean iced = false;

	/** the order this item is tied to */
	private int orderNumber;

	// Default constructor required by Jackson Java JSON-processor
	public OrderItem() {}

	public OrderItem(int orderNumber, DrinkType type, int shots, boolean iced) {
		this.orderNumber = orderNumber;
		this.type = type;
		this.shots = shots;
		this.iced = iced;
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public boolean isIced() {
		return this.iced;
	}

	public void setIced(boolean iced) {
		this.iced = iced;
	}

	public int getShots() {
		return shots;
	}

	public void setShots(int shots) {
		this.shots = shots;
	}

	public DrinkType getDrinkType() {
		return this.type;
	}

	public void setDrinkType(DrinkType type) {
		this.type = type;
	}

	public String toString() {
		return ((this.iced) ? "iced " : "hot ") + this.shots + " shot " + this.type;
}

}
