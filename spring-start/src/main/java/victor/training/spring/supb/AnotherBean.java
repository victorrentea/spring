package victor.training.spring.supb;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

public class AnotherBean {
    @EventListener
    @Order(5) // BAD = global coupling point between all the listeners in your app to this event
    public void alistener3(MyEvent event) {
        System.out.println("Event1 handler for my own event With data : " + event.getData());
    }

    // events : 2 questions:
    // do the publisher and listener share the same
    // - thread ? YES, all listeners are run in the same th as publisher, according to @Order
        // @Async
    // - transaction ? YES
        // Change with @TransactionalEventListener (AFTER COMMIT of the pubisher transaction)
    // vs a MQ: sending a message on Rabbit is PERSISTED
    // vs a method call: events are MUCH MORE COMPLICATED to debug/trace DON'T, unless in the next 2-3 sprints
            // you are planning to cut out an atom in a separate microservace.
}
