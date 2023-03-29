package victor.training.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.currentTimeMillis;

@SpringBootApplication
@Slf4j
@RestController
public class SSOApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SSOApplication.class)
                .run(args);
    }

    @EventListener(ApplicationStartedEvent.class)
    @Order
    public void printAppStarted() {
        log.info("🎈🎈🎈 SSO Application started🎈🎈🎈");
    }
    @GetMapping("api/user/current")
    public String getCurrentUsername() {
        KeyCloakUtils.printTheTokens();
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
