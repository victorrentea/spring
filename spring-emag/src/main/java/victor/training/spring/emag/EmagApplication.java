package victor.training.spring.emag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EmagApplication {
  public static void main(String[] args) {
    // NU folosesc un CommandLineRunner: acela ar rula și în @SpringBootTest, umplând testele de zgomot
    try (ConfigurableApplicationContext context = SpringApplication.run(EmagApplication.class, args)) {
      OfferGateway gateway = context.getBean(OfferGateway.class);

      System.out.println("Lanțul de oferte, în ordinea dată de @Order:");
      gateway.processors().forEach(p -> System.out.println("  -> " + p.getClass().getSimpleName()));

      Cart cart = new Cart(
          new CartItem("Laptop", Category.ELECTRONICS, 3000, 1),
          new CartItem("Mouse", Category.ELECTRONICS, 100, 3),
          new CartItem("Cafea", Category.FOOD, 50, 2))
          .withVoucher("EMAG50");

      gateway.applyOffers(cart);

      System.out.println("Oferte aplicate:");
      cart.appliedOffers().forEach(o -> System.out.println("  * " + o));
      System.out.println("Total de plată: " + cart.total() + " lei");
    }
  }
}
