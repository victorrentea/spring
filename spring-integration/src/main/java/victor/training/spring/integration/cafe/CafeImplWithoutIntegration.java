package victor.training.spring.integration.cafe;

import lombok.extern.slf4j.Slf4j;
import victor.training.spring.integration.cafe.model.Delivery;
import victor.training.spring.integration.cafe.model.Drink;
import victor.training.spring.integration.cafe.model.Order;
import victor.training.spring.integration.cafe.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CafeImplWithoutIntegration implements Cafe {
    private final Barista barista = new Barista();
    private final Waiter waiter = new Waiter();

    public void  placeOrder(Order order) {
        List<Drink> drinks = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            if (item.isIced()) {
                drinks.add(barista.prepareColdDrink(item));
            } else {
                drinks.add(barista.prepareHotDrink(item));
            }
        }

        Delivery delivery = waiter.prepareDelivery(drinks);
        log.info(delivery.toString());
    }

}
