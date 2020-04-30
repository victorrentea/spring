package org.springframework.integration.samples.cafe.annotation;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.samples.cafe.Order;
import org.springframework.integration.samples.cafe.OrderItem;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

@Component
public class OrderSplitter {

    private static Log logger = LogFactory.getLog(OrderSplitter.class);

    public List<OrderItem> split(Order order) {
        logger.warn("Splitting " + order);
        return order.getItems();
    }
}
