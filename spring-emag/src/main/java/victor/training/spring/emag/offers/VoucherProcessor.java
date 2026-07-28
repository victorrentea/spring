package victor.training.spring.emag.offers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import victor.training.spring.emag.Cart;
import victor.training.spring.emag.OfferProcessor;

import java.util.Map;

/**
 * PASUL 3: voucherul cadou, scăzut din totalul comenzii.
 * Rulează DUPĂ reducerile pe linii (se aplică pe ce a rămas)
 * și ÎNAINTE de transport (pentru că poate să te scoată din pragul de transport gratuit).
 */
@Order(3)
@Component
public class VoucherProcessor implements OfferProcessor {
  private static final Map<String, Double> VOUCHERS = Map.of(
      "EMAG50", 50.0,
      "BLACKFRIDAY100", 100.0);

  @Override
  public void apply(Cart cart) {
    if (cart.voucherCode() == null) {
      return; // Map.of(..).get(null) aruncă NPE, deci ies din timp
    }
    Double value = VOUCHERS.get(cart.voucherCode());
    if (value != null) {
      cart.addOrderDiscount(value);
      cart.logOffer("VOUCHER " + cart.voucherCode() + " -" + value + " lei");
    }
  }
}
