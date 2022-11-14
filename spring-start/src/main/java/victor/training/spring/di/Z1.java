package victor.training.spring.di;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class Z1 implements ZInterface {
    @Override
    public int prod() {
        System.out.println("smells like prod");
        return 1;
    }
}

@Service
@Profile("local") // hide this bean except when the profiles active include 'local'
@Primary  // makes this bean win any competion for injection
class Z2 implements ZInterface {

    @Override
    public int prod() {
        System.out.println("In local env");
        return -1; // only for local development
    }
}