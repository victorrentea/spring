package victor.training.spring.di;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.beans.Transient;

@Component
public class Startup implements CommandLineRunner {

    @EventListener(ApplicationStartedEvent.class)
    // you can hook into 12+ events thrown by the Sprign frm when it starts up
    // > build extension to spring
    public void event() {
        System.out.println("Hello ApplicationStartedEvent !");
    }

//    @Transactional // not work on @PostConstruct since @Transactional interceptor is not ready yet
    // at the time @PostConstruct hooks are called
    @PostConstruct // the most commont
    public void postConstructor() {
        System.out.println("Hello PostConstruct !");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Command lien args " + args);
    }
}
