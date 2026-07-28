package victor.training.spring.emag;

import java.util.ArrayList;
import java.util.List;

/**
 * Coșul de cumpărături. Procesatorii de oferte îl mutează pas cu pas:
 * unii taie din liniile individuale, alții din totalul comenzii, altul face transportul gratuit.
 */
public class Cart {
  public static final double STANDARD_SHIPPING = 19.99;

  private final List<CartItem> items = new ArrayList<>();
  private final List<String> appliedOffers = new ArrayList<>();
  private String voucherCode;
  private double orderDiscount;
  private double shippingCost = STANDARD_SHIPPING;

  public Cart(CartItem... items) {
    this.items.addAll(List.of(items));
  }

  public List<CartItem> items() {
    return items;
  }

  public Cart withVoucher(String voucherCode) {
    this.voucherCode = voucherCode;
    return this;
  }

  public String voucherCode() {
    return voucherCode;
  }

  /** Suma liniilor, după reducerile aplicate pe linii. */
  public double itemsTotal() {
    return items.stream().mapToDouble(CartItem::lineTotal).sum();
  }

  /** Cât plătește clientul: liniile − reducerile pe comandă + transportul. */
  public double total() {
    return Math.max(0, itemsTotal() - orderDiscount) + shippingCost;
  }

  public double shippingCost() {
    return shippingCost;
  }

  public void addOrderDiscount(double amount) {
    orderDiscount += amount;
  }

  public void makeShippingFree() {
    shippingCost = 0;
  }

  /** Jurnalul ofertelor aplicate — util ca să vedem în teste PRIN CE a trecut coșul. */
  public List<String> appliedOffers() {
    return appliedOffers;
  }

  public void logOffer(String description) {
    appliedOffers.add(description);
  }
}
