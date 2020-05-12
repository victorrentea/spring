package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${welcome.info}")
    private String welcomeInfo;

    @GetMapping("hello")
    public String showWelcomeInfo() {
        return welcomeInfo;
    }
}
