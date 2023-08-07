package victor.training.spring.supb;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class SomeBean {
    @EventListener
    @Order(10)
    public void listener2(MyEvent event) {
        System.out.println("Event2 handler for my own event With data : " + event.getData());
    }

}
