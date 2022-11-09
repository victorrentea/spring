package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LaStartup implements CommandLineRunner {
    @Autowired
    private Z z;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CLR " + z);
    }

    @PostConstruct // nu merge @Transcational pe el
    public void init() {
        System.out.println("post construct IoC");
    }

    @EventListener(ApplicationStartedEvent.class)
    public void eventu() {
        System.out.println("Event ");
    }




    /// Java standard dar nu SPring-friendly
    {
        System.out.println("instance initializer block " + z);
    }
    static {
        System.out.println("class initializer block " );
    }
}
