package victor.training.spring.emag.offers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import victor.training.spring.emag.Cart;
import victor.training.spring.emag.CartItem;
import victor.training.spring.emag.OfferProcessor;

/**
 * PASUL 1: "Iei 3+ bucăți din același produs → -10% pe linia aia."
 * Rulează primul pentru că lucrează pe PREȚUL DE LISTĂ: cantitatea e criteriul, nu prețul.
 */
@Order(1)
@Component
public class BulkDiscountProcessor implements OfferProcessor {
  private static final int MIN_QUANTITY = 3;
  private static final double PERCENT = 0.10;

  @Override
  public void apply(Cart cart) {
    for (CartItem item : cart.items()) {
      if (item.quantity() >= MIN_QUANTITY) {
        item.addLineDiscount(item.grossValue() * PERCENT);
        cart.logOffer("BULK -10% pe " + item.product());
      }
    }
  }
}
