package victor.training.spring.emag;

/**
 * Toate ofertele implementează asta. Spring le găsește pe toate și mi le injectează ca List.
 * ORDINEA în care rulează contează, de aceea fiecare implementare e adnotată cu @Order.
 */
public interface OfferProcessor {
  void apply(Cart cart);
}
