package victor.training.spring.bean;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimuEndpoint {
    @GetMapping("hello")
    public String method() {
        return "Hello! Frate@! faina chestia da are limitari: nu poti adauga clase noi/ metode noi, ci" +
               "doar sa dai si tu un pic la pacanele cand fixezi un bug";
    }
}
