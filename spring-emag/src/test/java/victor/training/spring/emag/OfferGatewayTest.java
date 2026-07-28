package victor.training.spring.emag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import victor.training.spring.emag.offers.BulkDiscountProcessor;
import victor.training.spring.emag.offers.CategoryPromoProcessor;
import victor.training.spring.emag.offers.FreeShippingProcessor;
import victor.training.spring.emag.offers.VoucherProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@SpringBootTest
class OfferGatewayTest {
  @Autowired
  OfferGateway gateway;

  @Test
  @DisplayName("1. Spring injectează TOȚI procesatorii, sortați după @Order")
  void chainIsSortedByOrderAnnotation() {
    assertThat(gateway.processors())
        .hasSize(4)
        .extracting(Object::getClass)
        .containsExactly( // exact în ordinea @Order(1..4), nu alfabetic, nu în ordinea declarării
            BulkDiscountProcessor.class,
            CategoryPromoProcessor.class,
            VoucherProcessor.class,
            FreeShippingProcessor.class);
  }

  @Test
  @DisplayName("2. Un coș realist trece prin tot lanțul, ofertă după ofertă")
  void fullChainOnARealisticCart() {
    Cart cart = new Cart(
        new CartItem("Laptop", Category.ELECTRONICS, 3000, 1),
        new CartItem("Mouse", Category.ELECTRONICS, 100, 3), // 3 buc => prinde bulk
        new CartItem("Cafea", Category.FOOD, 50, 2))
        .withVoucher("EMAG50");

    gateway.applyOffers(cart);

    // Laptop: 3000 -15% electro          = 2550
    // Mouse : 300 -10% bulk = 270, -15%  = 229.5   <-- electro se aplică pe cât a rămas după bulk
    // Cafea : 100 (nicio ofertă)         = 100
    assertThat(cart.itemsTotal()).isEqualTo(2879.5, within(0.01));
    // -50 voucher, iar 2829.5 >= 300 => transport gratuit
    assertThat(cart.shippingCost()).isZero();
    assertThat(cart.total()).isEqualTo(2829.5, within(0.01));

    assertThat(cart.appliedOffers()).containsExactly(
        "BULK -10% pe Mouse",
        "ZILELE ELECTRO -15% pe Laptop",
        "ZILELE ELECTRO -15% pe Mouse",
        "VOUCHER EMAG50 -50.0 lei",
        "TRANSPORT GRATUIT (peste 300.0 lei)");
  }

  @Test
  @DisplayName("3. ORDINEA CONTEAZĂ: voucherul te poate scoate din pragul de transport gratuit")
  void orderMatters_voucherBeforeFreeShipping() {
    // 320 lei de marfă: e peste pragul de 300... dar nu și după ce scad voucherul de 50
    Cart cart = new Cart(new CartItem("Rochie", Category.FASHION, 320, 1))
        .withVoucher("EMAG50");

    gateway.applyOffers(cart);

    assertThat(cart.shippingCost()).isEqualTo(Cart.STANDARD_SHIPPING); // transportul SE plătește
    assertThat(cart.total()).isEqualTo(320 - 50 + Cart.STANDARD_SHIPPING, within(0.01));

    // ...iar acum același coș, cu ULTIMII DOI procesatori în ordine inversă:
    Cart same = new Cart(new CartItem("Rochie", Category.FASHION, 320, 1))
        .withVoucher("EMAG50");
    List<OfferProcessor> gresit = List.of(new FreeShippingProcessor(), new VoucherProcessor());
    gresit.forEach(p -> p.apply(same));

    assertThat(same.shippingCost()).isZero(); // pragul s-a verificat pe 320, înainte de voucher
    assertThat(same.total()).isEqualTo(270, within(0.01)); // 19.99 lei pierduți de eMAG
  }

  @Test
  @DisplayName("4. Un coș pe care nu se prinde nicio ofertă rămâne neatins")
  void cartWithNoApplicableOffer() {
    Cart cart = new Cart(new CartItem("Cafea", Category.FOOD, 50, 2)); // fără voucher

    gateway.applyOffers(cart);

    assertThat(cart.appliedOffers()).isEmpty();
    assertThat(cart.itemsTotal()).isEqualTo(100, within(0.01));
    assertThat(cart.total()).isEqualTo(100 + Cart.STANDARD_SHIPPING, within(0.01));
  }
}
