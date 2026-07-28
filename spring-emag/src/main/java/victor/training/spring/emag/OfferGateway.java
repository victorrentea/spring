package victor.training.spring.emag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Aici e toată șmecheria: cer o List<OfferProcessor> și Spring îmi injectează
 * TOATE bean-urile care implementează interfața, sortate după @Order (crescător).
 * Nu enumăr nicăieri implementările: adaug un @Component nou și intră singur în lanț.
 */
@Service
public class OfferGateway {
  private final List<OfferProcessor> processors;

  @Autowired
  public OfferGateway(List<OfferProcessor> processors) {
    this.processors = processors;
  }

  public Cart applyOffers(Cart cart) {
    for (OfferProcessor processor : processors) {
      processor.apply(cart);
    }
    return cart;
  }

  /** Expus ca să pot verifica în test ordinea reală a lanțului. */
  public List<OfferProcessor> processors() {
    return processors;
  }
}
