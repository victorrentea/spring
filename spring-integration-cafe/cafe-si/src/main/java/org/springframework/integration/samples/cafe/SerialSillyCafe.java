package org.springframework.integration.samples.cafe;

import org.springframework.integration.samples.cafe.annotation.Barista;
import org.springframework.integration.samples.cafe.annotation.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SerialSillyCafe implements Cafe {
    private static final Logger log = Logger.getLogger(SerialSillyCafe.class.getCanonicalName());

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
