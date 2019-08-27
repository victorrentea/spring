package victor.training.spring.injection;

import javax.annotation.PostConstruct;

public class SecurityProvider {
    @PostConstruct
    public void init() {
        System.out.println("init secu provi");
    }
    public void gadila() {
    }
}
