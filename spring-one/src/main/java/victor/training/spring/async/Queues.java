package victor.training.spring.async;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

interface Queues {



    @Output("beerRequestsOut")
    MessageChannel beerRequestsOut();
    @Output("vodkaRequestsOut")
    MessageChannel vodkaRequestsOut();

    String BEER_REQUESTS_IN = "beerRequestsIn";
    @Input(BEER_REQUESTS_IN)
    SubscribableChannel beerRequestsIn();

    String VODKA_REQUESTS_IN = "vodkaRequestsIn";
    @Input(VODKA_REQUESTS_IN)
    SubscribableChannel vodkaRequestsIn();

    String DRINKS_RESULT_IN = "drinksResultIn";
    @Input(DRINKS_RESULT_IN)
    SubscribableChannel drinksResultIn();
    @Output("drinksResultOut")
    SubscribableChannel drinksResultOut();

}