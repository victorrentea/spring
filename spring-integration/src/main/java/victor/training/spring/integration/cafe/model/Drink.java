package victor.training.spring.integration.cafe.model;

import java.io.Serializable;

public class Drink implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean iced;

	private int shots;

	private DrinkType drinkType;

	private int orderNumber;

	// Default constructor required by Jackson Java JSON-processor
	public Drink() {}

	public Drink(int orderNumber, DrinkType drinkType, boolean iced, int shots) {
		this.orderNumber = orderNumber;
		this.drinkType = drinkType;
		this.iced = iced;
		this.shots = shots;
	}


	public int getOrderNumber() {
		return orderNumber;
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

	public DrinkType getDrinkType() {
		return this.drinkType;
	}

	public void setDrinkType(DrinkType drinkType) {
		this.drinkType = drinkType;
	}

	public int getShots() {
		return this.shots;
	}

	public void setShots(int shots) {
		this.shots = shots;
	}

	@Override
	public String toString() {
		return (iced?"Iced":"Hot")  + " " + drinkType.toString() + ", " + shots + " shots.";
	}

}
