package victor.training.spring.emag.offers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import victor.training.spring.emag.Cart;
import victor.training.spring.emag.CartItem;
import victor.training.spring.emag.Category;
import victor.training.spring.emag.OfferProcessor;

/**
 * PASUL 2: "Zilele Electro: -15% la categoria ELECTRONICS."
 * Rulează DUPĂ bulk pentru că se aplică pe cât a mai rămas din linie, nu pe prețul de listă.
 */
@Order(2)
@Component
public class CategoryPromoProcessor implements OfferProcessor {
  private static final Category PROMO_CATEGORY = Category.ELECTRONICS;
  private static final double PERCENT = 0.15;

  @Override
  public void apply(Cart cart) {
    for (CartItem item : cart.items()) {
      if (item.category() == PROMO_CATEGORY) {
        item.addLineDiscount(item.lineTotal() * PERCENT);
        cart.logOffer("ZILELE ELECTRO -15% pe " + item.product());
      }
    }
  }
}
