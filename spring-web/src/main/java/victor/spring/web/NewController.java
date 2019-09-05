package victor.spring.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("meta")
public class NewController {
    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
    @GetMapping("alertEveryone")
    public String alert() {
        return "sending SMS to everyone";
    }
    @GetMapping("monitor/heap")
    public String heap() {
        return "heap";
    }
}
