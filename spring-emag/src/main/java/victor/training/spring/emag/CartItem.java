package victor.training.spring.emag;

/** O linie din coș: produsul, câte bucăți și ce reducere s-a acumulat pe linia asta. */
public class CartItem {
  private final String product;
  private final Category category;
  private final double unitPrice;
  private final int quantity;
  private double lineDiscount;

  public CartItem(String product, Category category, double unitPrice, int quantity) {
    this.product = product;
    this.category = category;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
  }

  public String product() {
    return product;
  }

  public Category category() {
    return category;
  }

  public int quantity() {
    return quantity;
  }

  /** Cât face linia ÎNAINTE de orice reducere. */
  public double grossValue() {
    return unitPrice * quantity;
  }

  /** Cât face linia DUPĂ reducerile aplicate până acum. */
  public double lineTotal() {
    return grossValue() - lineDiscount;
  }

  public void addLineDiscount(double amount) {
    lineDiscount += amount;
  }

  @Override
  public String toString() {
    return quantity + " x " + product + " = " + lineTotal();
  }
}
