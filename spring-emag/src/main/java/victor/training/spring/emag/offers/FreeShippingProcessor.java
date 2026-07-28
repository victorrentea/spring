package victor.training.spring.emag.offers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import victor.training.spring.emag.Cart;
import victor.training.spring.emag.OfferProcessor;

/**
 * PASUL 4: "Transport gratuit peste 300 lei."
 * Rulează ULTIMUL, pentru că pragul se verifică pe suma REAL plătită,
 * adică după ce toate celelalte oferte au tăiat din ea.
 */
@Order(4)
@Component
public class FreeShippingProcessor implements OfferProcessor {
  private static final double THRESHOLD = 300;

  @Override
  public void apply(Cart cart) {
    // cart.total() include deja transportul; ne interesează doar marfa de plată
    double payableGoods = cart.total() - cart.shippingCost();
    if (payableGoods >= THRESHOLD) {
      cart.makeShippingFree();
      cart.logOffer("TRANSPORT GRATUIT (peste " + THRESHOLD + " lei)");
    }
  }
}
